package controller;

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

public class LevelController {

    private final GameModel       gameModel;
    private final List<ChickenModel> chickens;
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
            BossChickenModel boss = new BossChickenModel(350, 50, config.getLevel());
            chickens.add(boss);
            bossSpawned = true;
        }

        // Update movement
        Iterator<ChickenModel> it = chickens.iterator();
        while (it.hasNext()) {
            ChickenModel c = it.next();
            if (!c.isAlive()) {
                it.remove();
                continue;
            }
            c.move();
            c.tickShoot();

            if (c instanceof EggChickenModel) ((EggChickenModel)c).tickEgg();
            if (c instanceof BossChickenModel) ((BossChickenModel)c).tickSkills();

            // Biên
            if (c.getX() <= 0 || c.getX() + c.getW() >= 800) {
                c.reverseDir();
            }
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

        ChickenModel cm = isEgg ? new EggChickenModel(x, y) : new NormalChickenModel(x, y);
        cm.setSpeed(config.getChickenSpeed());
        chickens.add(cm);
        chickenSpawnedCount++;
    }

    public List<ChickenModel> getChickens() { return chickens; }
}
