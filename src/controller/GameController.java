package controller;

import controller.entity.PlayerController;
import controller.entity.bullet.BulletController;
import controller.item.ItemController;
import controller.skill.SkillController;
import model.GameModel;
import model.entity.PlayerModel;
import model.entity.bullet.BulletModel;
import model.entity.bullet.ChickenBulletModel;
import model.entity.bullet.DoubleBulletModel;
import model.entity.bullet.SingleBulletModel;
import model.entity.chicken.BossChickenModel;
import model.entity.chicken.ChickenModel;
import model.entity.chicken.NormalChickenModel;
import model.entity.egg.EggModel;
import model.item.ItemModel;
import model.item.PowerUpModel;
import model.item.WeaponItemModel;
import model.weapon.WeaponType;
import util.SoundManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameController implements Updatable {

    private final GameModel       gameModel;
    private final PlayerController playerController;
    private final LevelController levelController;
    private final SkillController skillController;

    private final List<BulletController> bullets;
    private final List<ItemController>   items;

    private final Random random;

    private boolean isShooting = false;
    private boolean isFinishedSoundPlayed = false;

    private static final int GAME_WIDTH  = 800;
    private static final int GAME_HEIGHT = 600;

    public GameController() {
        this.gameModel       = new GameModel();
        this.playerController = new PlayerController(new PlayerModel());
        this.levelController = new LevelController(gameModel);
        this.skillController = new SkillController();
        
        this.bullets         = new ArrayList<>();
        this.items           = new ArrayList<>();
        this.random          = new Random();
    }

    public void startGame() {
        gameModel.reset();
        getPlayer().reset();
        bullets.clear();
        items.clear();
        levelController.startLevel();
        isFinishedSoundPlayed = false;
    }

    @Override
    public void update() {
        gameModel.update();

        if (gameModel.isPause()) return;

        if (gameModel.isLevelUp()) {
            bullets.clear();
            return;
        }

        if (gameModel.isGameOver()) {
            if (!isFinishedSoundPlayed) {
                if (gameModel.getPhase() == GameModel.Phase.WIN) {
                    SoundManager.getInstance().stopBGM();
                    SoundManager.getInstance().play("win");
                } else {
                    SoundManager.getInstance().stopBGM();
                    SoundManager.getInstance().play("game_over");
                }
                isFinishedSoundPlayed = true;
            }
            return;
        }

        playerController.update();
        
        if (isShooting && getPlayer().canShoot()) {
            firePlayerBullet();
            getPlayer().resetShootTimer();
        }

        levelController.update();
        handleChickenShooting();
        updateBullets();
        updateItems();
        checkCollisions();

        if (!getPlayer().isAlive()) {
            gameModel.setLose();
        }
    }

    public void setShooting(boolean shooting) {
        this.isShooting = shooting;
    }

    private void handleChickenShooting() {
        List<ChickenModel> chickens = levelController.getChickens();
        for (ChickenModel c : chickens) {
            if (!c.isAlive()) continue;

            if (c.canShoot()) {
                if (c instanceof BossChickenModel boss) {
                    SkillController.BossAttackResult attack = skillController.updateBossSkills(boss);
                    for (ChickenBulletModel bullet : attack.bullets()) {
                        bullets.add(new BulletController(bullet));
                    }
                    for (NormalChickenModel summon : attack.summons()) {
                        levelController.summonChicken(summon);
                    }
                } else {
                    bullets.add(new BulletController(ChickenBulletModel.straight(c.getCenterX(), c.getY() + c.getH())));
                }
                c.resetShootTimer();
            }
        }
    }

    private void updateBullets() {
        Iterator<BulletController> it = bullets.iterator();
        while (it.hasNext()) {
            BulletController bc = it.next();
            bc.update();
            BulletModel b = bc.getModel();
            if (b.getY() < -50 || b.getY() > GAME_HEIGHT + 50 || b.getX() < -50 || b.getX() > GAME_WIDTH + 50) {
                it.remove();
            }
        }
    }

    private void updateItems() {
        Iterator<ItemController> it = items.iterator();
        while (it.hasNext()) {
            ItemController ic = it.next();
            ic.update();
            ItemModel item = ic.getModel();
            if (!item.isAlive() || item.getY() > GAME_HEIGHT) {
                it.remove();
            }
        }
    }

    private void firePlayerBullet() {
        WeaponType type = getPlayer().getWeapon().getType();
        int damage      = getPlayer().getWeapon().getDamage();
        boolean pierce  = getPlayer().getWeapon().isPierce();
        float px        = getPlayer().getCenterX();
        float py        = getPlayer().getY();

        if (type == WeaponType.SINGLE) {
            bullets.add(new BulletController(new SingleBulletModel(px - SingleBulletModel.WIDTH/2f, py, damage, pierce)));
        } else {
            bullets.add(new BulletController(new DoubleBulletModel(px - 10, py, damage, pierce, DoubleBulletModel.Side.LEFT)));
            bullets.add(new BulletController(new DoubleBulletModel(px + 4,  py, damage, pierce, DoubleBulletModel.Side.RIGHT)));
        }
        
        SoundManager.getInstance().play("shoot");
    }

    private void spawnItem(float x, float y, int scoreValue) {
        items.add(new ItemController(new PowerUpModel(x, y, PowerUpModel.PowerUpType.SCORE_DRUMSTICK)));
        if (random.nextFloat() < 0.15f) { 
            int r = random.nextInt(100);
            ItemModel buff;
            if (r < 40) {
                WeaponType type = random.nextBoolean() ? WeaponType.SINGLE : WeaponType.DOUBLE;
                buff = new WeaponItemModel(x, y + 10, type);
            } else if (r < 70) {
                buff = new PowerUpModel(x, y + 10, PowerUpModel.PowerUpType.HEAL);
            } else {
                buff = new PowerUpModel(x, y + 10, random.nextBoolean() ? PowerUpModel.PowerUpType.SHIELD : PowerUpModel.PowerUpType.DAMAGE_UP);
            }
            items.add(new ItemController(buff));
        }
    }

    private void checkCollisions() {
        List<ChickenModel> chickens = levelController.getChickens();
        List<EggModel> eggs = levelController.getActiveEggs();

        // --- Bullet Collisions ---
        Iterator<BulletController> bIt = bullets.iterator();
        while (bIt.hasNext()) {
            BulletController bc = bIt.next();
            BulletModel b = bc.getModel();
            boolean hit = false;
            if (b.getOwner() == BulletModel.Owner.PLAYER) {
                for (ChickenModel c : chickens) {
                    if (c.isAlive() && c.collidesWith(b)) {
                        c.takeDamage(b.getDamage());
                        if (!c.isAlive()) {
                            spawnItem(c.getX(), c.getY(), c.getScoreValue());
                            SoundManager.getInstance().play(c instanceof BossChickenModel ? "boss_die" : "chicken_die");
                        }
                        hit = true;
                        if (!b.isPierce()) break;
                    }
                }
                
                if (!hit || b.isPierce()) {
                    for (EggModel egg : eggs) {
                        if (egg.isAlive() && egg.collidesWith(b)) {
                            egg.takeDamage(b.getDamage()); 
                            if (!egg.isAlive()) SoundManager.getInstance().play("egg_break");
                            hit = true;
                            if (!b.isPierce()) break;
                        }
                    }
                }
            } else {
                if (getPlayer().isAlive() && getPlayer().collidesWith(b)) {
                    getPlayer().takeDamage(b.getDamage());
                    SoundManager.getInstance().play("hit");
                    hit = true;
                }
            }
            if (hit && !b.isPierce()) bIt.remove();
        }

        // --- Egg vs Player Collision ---
        Iterator<EggModel> eIt = eggs.iterator();
        while (eIt.hasNext()) {
            EggModel egg = eIt.next();
            if (egg.isAlive() && getPlayer().collidesWith(egg)) {
                getPlayer().takeDamage(1); // Gây dame cho player
                egg.takeDamage(999); // Trứng vỡ ngay lập tức
                SoundManager.getInstance().play("hit");
                SoundManager.getInstance().play("egg_break");
                // Khi trứng vỡ do va chạm với player, nó sẽ tự nở ra gà con trong LevelController.updateEggs()
            }
        }

        // --- Item vs Player Collision ---
        Iterator<ItemController> iIt = items.iterator();
        while (iIt.hasNext()) {
            ItemController ic = iIt.next();
            ItemModel item = ic.getModel();
            if (item.isAlive() && getPlayer().collidesWith(item)) {
                if (item instanceof PowerUpModel && ((PowerUpModel)item).getPowerUpType() == PowerUpModel.PowerUpType.SCORE_DRUMSTICK) {
                    gameModel.addScore(100);
                } else {
                    item.applyEffect(getPlayer());
                }
                SoundManager.getInstance().play("item");
                iIt.remove();
            }
        }

        // --- Chicken Body vs Player Collision ---
        for (ChickenModel c : chickens) {
            if (c.isAlive() && getPlayer().isAlive() && c.collidesWith(getPlayer())) {
                getPlayer().takeDamage(1);
                c.takeDamage(999); 
                SoundManager.getInstance().play("hit");
            }
        }
    }

    public GameModel       getGameModel() { return gameModel; }
    public PlayerModel     getPlayer()    { return playerController.getModel(); }
    public List<ChickenModel> getChickens() { return levelController.getChickens(); }
    public List<EggModel>     getActiveEggs() { return levelController.getActiveEggs(); }
    public List<BulletModel>  getBullets()  { 
        List<BulletModel> models = new ArrayList<>();
        for (BulletController bc : bullets) models.add(bc.getModel());
        return models; 
    }
    public List<ItemModel>    getItems()    { 
        List<ItemModel> models = new ArrayList<>();
        for (ItemController ic : items) models.add(ic.getModel());
        return models; 
    }
    public SkillController getSkillController() { return skillController; }
}
