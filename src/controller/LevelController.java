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
    private int   currentLevel; // Để theo dõi khi level thay đổi

    public LevelController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.chickens  = new ArrayList<>();
        this.random    = new Random();
        this.currentLevel = gameModel.getLevel();
    }

    public void startLevel() {
        chickens.clear();
        chickenSpawnedCount = 0;
        spawnTimer          = 0;
        bossSpawned         = false;
        currentLevel = gameModel.getLevel();
        System.out.println("Starting Level: " + currentLevel);
    }

    @Override
    public void update() {
        if (!gameModel.isPlaying()) return;

        // Nếu level trong GameModel thay đổi, reset controller cho level mới
        if (gameModel.getLevel() != currentLevel) {
            startLevel();
        }

        LevelConfig config = LevelConfig.get(gameModel.getLevel());

        // --- Spawn logic ---
        int totalToSpawn = config.getNormalCount() + config.getEggCount();
        
        if (chickenSpawnedCount < totalToSpawn) {
            if (spawnTimer > 0) {
                spawnTimer--;
            } else {
                spawnOneChicken(config);
                spawnTimer = 60; // 1s spawn 1 con
            }
        } else if (config.hasBoss() && !bossSpawned && chickens.isEmpty()) {
            // Hết gà thường -> Gọi boss
            BossChickenModel bossModel = new BossChickenModel(350, 50, config.getLevel());
            chickens.add(new BossChickenController(bossModel));
            bossSpawned = true;
        }

        // --- Update chickens ---
        Iterator<ChickenController> it = chickens.iterator();
        while (it.hasNext()) {
            ChickenController c = it.next();
            if (!c.getModel().isAlive()) {
                it.remove();
                continue;
            }
            c.update();
        }

        // --- Check Level Clear ---
        boolean allDead = chickens.isEmpty();
        boolean spawnedAll = chickenSpawnedCount >= totalToSpawn;
        boolean bossDone   = !config.hasBoss() || (bossSpawned && allDead);

        if (spawnedAll && allDead && bossDone) {
            // Chỉ gọi nextLevel() một lần
            gameModel.nextLevel();
            // startLevel() sẽ được gọi ở đầu vòng update kế tiếp do check (gameModel.getLevel() != currentLevel)
        }
    }

    private void spawnOneChicken(LevelConfig config) {
        float x = 50 + random.nextInt(700);
        float y = 50 + random.nextInt(200);
        
        // Tính toán tỉ lệ spawn trứng dựa trên config
        // Nếu đã spawn hết gà thường, thì bắt buộc là gà trứng, và ngược lại
        int totalToSpawn = config.getNormalCount() + config.getEggCount();
        
        // Ở đây ta đơn giản hóa logic spawn: 
        // Nếu level có eggCount > 0, random giữa thường và trứng
        boolean canSpawnEgg = config.getEggCount() > 0;
        boolean isEgg = canSpawnEgg && random.nextBoolean();

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
