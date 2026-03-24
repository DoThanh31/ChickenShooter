package controller;

import controller.entity.chicken.*;
import model.GameModel;
import model.LevelConfig;
import model.entity.chicken.BossChickenModel;
import model.entity.chicken.ChickenModel;
import model.entity.chicken.EggChickenModel;
import model.entity.chicken.NormalChickenModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LevelController implements Updatable {

    private final GameModel       gameModel;
    private final List<ChickenController> chickens;
    private final Random random;

    private int   chickenSpawnedCount;
    private int   spawnTimer;
    private boolean bossSpawned;

    public LevelController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.chickens  = new ArrayList<>();
        this.random    = new Random();
    }

    public void startLevel() {
        chickens.clear();
        chickenSpawnedCount = 0;
        spawnTimer          = 0;
        bossSpawned         = false;
    }

    @Override
    public void update() {
        if (!gameModel.isPlaying()) return;

        LevelConfig config = LevelConfig.get(gameModel.getLevel());

        // Spawn gà
        if (chickenSpawnedCount < config.getNormalCount() + config.getEggCount()) {
            if (spawnTimer > 0) {
                spawnTimer--;
            } else {
                spawnOneChicken(config);
                spawnTimer = 60; // 1s
            }
        } else if (config.hasBoss() && !bossSpawned && chickens.isEmpty()) {
            // Hết gà thường -> Gọi boss
            BossChickenModel bossModel = new BossChickenModel(350, 50, config.getLevel());
            chickens.add(new BossChickenController(bossModel));
            bossSpawned = true;
        }

        // Update movement
        Iterator<ChickenController> it = chickens.iterator();
        while (it.hasNext()) {
            ChickenController c = it.next();
            if (!c.getModel().isAlive()) {
                it.remove();
                continue;
            }
            c.update();
        }

        // Check clear
        boolean allDead = chickens.isEmpty();
        boolean spawnedAll = chickenSpawnedCount >= (config.getNormalCount() + config.getEggCount());
        boolean bossDone   = !config.hasBoss() || (bossSpawned && allDead);

        if (spawnedAll && allDead && bossDone) {
            gameModel.nextLevel();
            startLevel();
        }
    }

    private void spawnOneChicken(LevelConfig config) {
        float x = 50 + random.nextInt(700);
        float y = 50 + random.nextInt(200);
        boolean isEgg = random.nextBoolean() && config.getEggCount() > 0;

        if (isEgg) {
             EggChickenModel cm = new EggChickenModel(x, y);
             cm.setSpeed(config.getChickenSpeed());
             chickens.add(new EggChickenController(cm));
        } else {
             NormalChickenModel cm = new NormalChickenModel(x, y);
             cm.setSpeed(config.getChickenSpeed());
             chickens.add(new NormalChickenController(cm));
        }
        chickenSpawnedCount++;
    }

    public List<ChickenModel> getChickens() { 
        List<ChickenModel> models = new ArrayList<>();
        for (ChickenController c : chickens) {
            models.add(c.getModel());
        }
        return models; 
    }
}