package controller;

import controller.entity.PlayerController;
import controller.entity.bullet.BulletController;
import controller.item.ItemController;
import model.GameModel;
import model.entity.PlayerModel;
import model.entity.bullet.BulletModel;
import model.entity.bullet.ChickenBulletModel;
import model.entity.bullet.DoubleBulletModel;
import model.entity.bullet.SingleBulletModel;
import model.entity.chicken.BossChickenModel;
import model.entity.chicken.ChickenModel;
import model.entity.chicken.EggChickenModel;
import model.item.ItemModel;
import model.item.PowerUpModel;
import model.item.WeaponItemModel;
import model.weapon.WeaponType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameController implements Updatable {

    private final GameModel       gameModel;
    private final PlayerController playerController;
    private final LevelController levelController;

    // Quản lý đạn và item chung ở đây
    private final List<BulletController> bullets;
    private final List<ItemController>   items;

    private final Random random;

    // Kích thước màn hình (để check out of bounds)
    private static final int GAME_WIDTH  = 800;
    private static final int GAME_HEIGHT = 600;

    public GameController() {
        this.gameModel       = new GameModel();
        this.playerController = new PlayerController(new PlayerModel());
        // LevelController sẽ handle việc spawn gà theo level
        this.levelController = new LevelController(gameModel);
        
        this.bullets         = new ArrayList<>();
        this.items           = new ArrayList<>();
        this.random          = new Random();
    }

    /** Khởi tạo / Reset game */
    public void startGame() {
        gameModel.reset();
        getPlayer().reset();
        
        bullets.clear();
        items.clear();
        
        levelController.startLevel();
    }

    /** Game Loop chính (gọi từ GameThread/Timer) */
    @Override
    public void update() {
        // Nếu pause hoặc game over thì không update logic game
        if (gameModel.isPause() || gameModel.isGameOver()) return;

        // 1. Update Player
        playerController.update();
        if (getPlayer().canShoot()) {
            firePlayerBullet();
            getPlayer().resetShootTimer();
        }

        // 2. Update Level (Spawn & Move Chickens)
        levelController.update();
        
        // 3. Cho gà bắn đạn (Logic này có thể nằm trong LevelController hoặc ở đây)
        handleChickenShooting();

        // 4. Update Bullets & Items
        updateBullets();
        updateItems();

        // 5. Xử lý va chạm
        checkCollisions();

        // 6. Check điều kiện thua
        if (!getPlayer().isAlive()) {
            gameModel.setLose();
        }
    }

    // ── Update Sub-systems ───────────────────────────────────

    private void handleChickenShooting() {
        List<ChickenModel> chickens = levelController.getChickens();
        for (ChickenModel c : chickens) {
            if (!c.isAlive()) continue;

            if (c.canShoot()) {
                if (c instanceof BossChickenModel boss) {
                    fireBossBullet(boss);
                } else if (c instanceof EggChickenModel eggC) {
                    // Gà trứng chỉ thả trứng khi đến hạn
                    if (eggC.canDropEgg()) {
                        // Trứng rơi thẳng
                        bullets.add(new BulletController(ChickenBulletModel.straight(
                                eggC.getCenterX(), 
                                eggC.getY() + eggC.getH()
                        )));
                        eggC.resetEggTimer();
                    }
                } else {
                    // Gà thường bắn đạn thường
                    bullets.add(new BulletController(ChickenBulletModel.straight(
                            c.getCenterX(), 
                            c.getY() + c.getH()
                    )));
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

            // Xóa đạn ra khỏi màn hình
            if (b.getY() < -50 || b.getY() > GAME_HEIGHT + 50 || 
                b.getX() < -50 || b.getX() > GAME_WIDTH + 50) {
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

    // ── Actions ──────────────────────────────────────────────

    private void firePlayerBullet() {
        WeaponType type = getPlayer().getWeapon().getType();
        int damage      = getPlayer().getWeapon().getDamage();
        boolean pierce  = getPlayer().getWeapon().isPierce();
        float px        = getPlayer().getCenterX();
        float py        = getPlayer().getY();

        if (type == WeaponType.SINGLE) {
            bullets.add(new BulletController(new SingleBulletModel(px - SingleBulletModel.WIDTH/2f, py, damage, pierce)));
        } else {
            // Double: 2 viên song song
            bullets.add(new BulletController(new DoubleBulletModel(px - 10, py, damage, pierce, DoubleBulletModel.Side.LEFT)));
            bullets.add(new BulletController(new DoubleBulletModel(px + 4,  py, damage, pierce, DoubleBulletModel.Side.RIGHT)));
        }
    }

    private void fireBossBullet(BossChickenModel boss) {
        float bx = boss.getCenterX();
        float by = boss.getY() + boss.getH();

        // 1. Luôn bắn 1 viên thẳng (nếu muốn khó hơn)
        bullets.add(new BulletController(ChickenBulletModel.straight(bx, by)));

        // 2. Skill Spread Shot (Bắn tỏa)
        if (boss.canSpread()) {
            // Bắn 3 viên tỏa ra
            bullets.add(new BulletController(ChickenBulletModel.angled(bx, by, -2f, 4f)));
            bullets.add(new BulletController(ChickenBulletModel.angled(bx, by,  0f, 4f)));
            bullets.add(new BulletController(ChickenBulletModel.angled(bx, by,  2f, 4f)));
            boss.resetSpread();
        }

        // 3. Skill Laser (Bắn chùm nhanh - ở đây mô phỏng bằng đạn nhanh)
        if (boss.canLaser()) {
            bullets.add(new BulletController(ChickenBulletModel.angled(bx, by, 0f, 8f))); // đạn rất nhanh
            boss.resetLaser();
        }
    }

    private void spawnItem(float x, float y) {
        // Tỉ lệ rớt item: 25%
        if (random.nextFloat() > 0.25f) return;

        int r = random.nextInt(100);
        ItemModel item;

        if (r < 40) { // 0-39: Vũ khí
            // Random loại đạn
            WeaponType type = random.nextBoolean() ? WeaponType.SINGLE : WeaponType.DOUBLE;
            item = new WeaponItemModel(x, y, type);
        } else if (r < 70) { // 40-69: Hồi máu
            item = new PowerUpModel(x, y, PowerUpModel.PowerUpType.HEAL);
        } else { // 70-99: Khiên hoặc Tăng dame
            item = new PowerUpModel(x, y, random.nextBoolean() 
                    ? PowerUpModel.PowerUpType.SHIELD 
                    : PowerUpModel.PowerUpType.DAMAGE_UP);
        }
        items.add(new ItemController(item));
    }

    // ── Collision Detection ──────────────────────────────────

    private void checkCollisions() {
        List<ChickenModel> chickens = levelController.getChickens();

        // Check Bullet vs Entities
        Iterator<BulletController> bIt = bullets.iterator();
        while (bIt.hasNext()) {
            BulletController bc = bIt.next();
            BulletModel b = bc.getModel();
            boolean hit = false;

            if (b.getOwner() == BulletModel.Owner.PLAYER) {
                // Đạn Player bắn trúng Gà?
                for (ChickenModel c : chickens) {
                    if (c.isAlive() && c.collidesWith(b)) {
                        c.takeDamage(b.getDamage());
                        
                        // Nếu gà chết -> Score + Drop Item
                        if (!c.isAlive()) {
                            gameModel.addScore(c.getScoreValue());
                            spawnItem(c.getX(), c.getY());
                        }
                        
                        hit = true;
                        if (!b.isPierce()) break; // Nếu ko xuyên thấu thì dừng check gà khác
                    }
                }
            } else {
                // Đạn Gà/Boss bắn trúng Player?
                if (getPlayer().isAlive() && getPlayer().collidesWith(b)) {
                    getPlayer().takeDamage(b.getDamage());
                    hit = true;
                }
            }

            // Nếu trúng và ko xuyên -> Xóa đạn
            if (hit && !b.isPierce()) {
                bIt.remove();
            }
        }

        // Check Player vs Item
        Iterator<ItemController> iIt = items.iterator();
        while (iIt.hasNext()) {
            ItemController ic = iIt.next();
            ItemModel item = ic.getModel();
            if (item.isAlive() && getPlayer().collidesWith(item)) {
                item.applyEffect(getPlayer());
                iIt.remove(); // Ăn xong thì mất
            }
        }

        // Check Player vs Body (Va chạm trực tiếp)
        for (ChickenModel c : chickens) {
            if (c.isAlive() && getPlayer().isAlive() && c.collidesWith(getPlayer())) {
                getPlayer().takeDamage(1); // Mất máu khi đâm
                // Tùy game design: Gà có thể chết luôn hoặc ko
                c.takeDamage(999); 
            }
        }
    }

    // ── Getters for View ─────────────────────────────────────

    public GameModel       getGameModel() { return gameModel; }
    public PlayerModel     getPlayer()    { return playerController.getModel(); }
    public List<ChickenModel> getChickens() { return levelController.getChickens(); }
    public List<BulletModel>  getBullets()  { 
        List<BulletModel> models = new ArrayList<>();
        for (BulletController bc : bullets) {
            models.add(bc.getModel());
        }
        return models; 
    }
    public List<ItemModel>    getItems()    { 
        List<ItemModel> models = new ArrayList<>();
        for (ItemController ic : items) {
            models.add(ic.getModel());
        }
        return models; 
    }
}