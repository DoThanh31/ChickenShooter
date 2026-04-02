package controller;

import controller.entity.chicken.*;
import controller.entity.egg.*;
import model.GameModel;
import model.LevelConfig;
import model.entity.chicken.BossChickenModel;
import model.entity.chicken.ChickenModel;
import model.entity.chicken.EggChickenModel;
import model.entity.chicken.NormalChickenModel;
import model.entity.egg.BabyChickenModel;
import model.entity.egg.EggModel;
import util.SoundManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LevelController implements Updatable {

    private final GameModel       gameModel;
    private final List<ChickenController> chickens;
    private final List<EggModel> activeEggs;
    private final Random random;

    private int   chickenSpawnedCount;
    private int   fallingEggSpawnedCount;
    private int   spawnTimer;
    private boolean bossSpawned;
    private int   currentLevel;

    public LevelController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.chickens  = new ArrayList<>();
        this.activeEggs = new ArrayList<>();
        this.random    = new Random();
        this.currentLevel = gameModel.getLevel();
    }

    public void startLevel() {
        chickens.clear();
        activeEggs.clear();
        chickenSpawnedCount = 0;
        fallingEggSpawnedCount = 0;
        spawnTimer          = 0;
        bossSpawned         = false;
        currentLevel = gameModel.getLevel();
        System.out.println("Starting Level: " + currentLevel);
    }

    @Override
    public void update() {
        if (!gameModel.isPlaying()) return;

        if (gameModel.getLevel() != currentLevel) {
            startLevel();
        }

        LevelConfig config = LevelConfig.get(gameModel.getLevel());

        handleSpawning(config);

        Iterator<ChickenController> it = chickens.iterator();
        while (it.hasNext()) {
            ChickenController c = it.next();
            if (!c.getModel().isAlive()) {
                it.remove();
                continue;
            }
            c.update();
        }

        updateEggs();

        checkLevelClear(config);
    }

    private void handleSpawning(LevelConfig config) {
        int totalChickenToSpawn = config.getNormalCount() + config.getEggCount();
        int totalFallingEggs = config.getFallingEggCount();

        if (spawnTimer > 0) {
            spawnTimer--;
        } else {
            if (chickenSpawnedCount < totalChickenToSpawn) {
                spawnOneChicken(config);
                spawnTimer = 60;
            } 
            else if (fallingEggSpawnedCount < totalFallingEggs) {
                spawnOneFallingEgg();
                spawnTimer = 45;
            }
            else if (config.hasBoss() && !bossSpawned && chickens.isEmpty() && activeEggs.isEmpty()) {
                BossChickenModel bossModel = new BossChickenModel(350, 50, config.getLevel());
                chickens.add(new BossChickenController(bossModel));
                bossSpawned = true;
                
                // PHÁT ÂM THANH KHI BOSS XUẤT HIỆN
                SoundManager.getInstance().play("boss_spawn");
            }
        }
    }

    private void updateEggs() {
        Iterator<EggModel> eIt = activeEggs.iterator();
        while (eIt.hasNext()) {
            EggModel egg = eIt.next();
            egg.fall();
            if (!egg.isAlive() || egg.getY() > 580) {
                if (!egg.isAlive()) spawnBabyChicken(egg.getX(), egg.getY());
                eIt.remove();
            }
        }
    }

    private void spawnOneChicken(LevelConfig config) {
        float x = 50 + random.nextInt(700);
        float y = 50 + random.nextInt(200);
        
        boolean canSpawnEgg = config.getEggCount() > 0;
        boolean isEgg = canSpawnEgg && random.nextBoolean();

        if (isEgg) {
             EggChickenModel cm = new EggChickenModel(x, y);
             cm.setSpeed(config.getChickenSpeed());
             chickens.add(new EggChickenController(cm, this));
        } else {
             NormalChickenModel cm = new NormalChickenModel(x, y);
             cm.setSpeed(config.getChickenSpeed());
             chickens.add(new NormalChickenController(cm));
        }
        chickenSpawnedCount++;
    }

    private void spawnOneFallingEgg() {
        float x = 50 + random.nextInt(700);
        float y = -30;
        activeEggs.add(new EggModel(x, y));
        fallingEggSpawnedCount++;
    }

    public void addDroppedEgg(float x, float y) {
        activeEggs.add(new EggModel(x, y));
    }

    public void summonChicken(NormalChickenModel chicken) {
        chickens.add(new NormalChickenController(chicken));
    }

    private void spawnBabyChicken(float x, float y) {
        BabyChickenModel baby = new BabyChickenModel(x, y);
        chickens.add(new BabyChickenController(baby));
    }

    private void checkLevelClear(LevelConfig config) {
        boolean spawnedAll = chickenSpawnedCount >= (config.getNormalCount() + config.getEggCount());
        boolean eggsSpawnedAll = fallingEggSpawnedCount >= config.getFallingEggCount();
        boolean allDead = chickens.isEmpty();
        boolean eggsDone = activeEggs.isEmpty();
        boolean bossDone = !config.hasBoss() || (bossSpawned && allDead);

        if (spawnedAll && eggsSpawnedAll && allDead && eggsDone && bossDone) {
            gameModel.nextLevel();
        }
    }

    public List<ChickenModel> getChickens() { 
        List<ChickenModel> models = new ArrayList<>();
        for (ChickenController c : chickens) models.add(c.getModel());
        return models; 
    }

    public List<EggModel> getActiveEggs() { return activeEggs; }
}
