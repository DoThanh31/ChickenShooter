package model;


public class LevelConfig {

    private final int   level;
    private final int   normalCount;
    private final int   eggCount;
    private final int   fallingEggCount; // Số lượng trứng tự rơi (mới)
    private final boolean hasBoss;
    private final float chickenSpeed;

    private static final LevelConfig[] CONFIGS = {
            new LevelConfig(1,  8, 0, 0, false, 1.0f),
            new LevelConfig(2, 10, 0, 0, false, 1.3f),
            new LevelConfig(3,  0, 0, 15, false, 1.5f), // LEVEL 3: CHỈ TRỨNG RƠI (15 quả)
            new LevelConfig(4,  8, 4, 10, false,  1.8f),
            new LevelConfig(5,  4, 4, 10, true,  2.0f),
    };

    private LevelConfig(int level, int normalCount, int eggCount, int fallingEggCount,
                        boolean hasBoss, float chickenSpeed) {
        this.level           = level;
        this.normalCount     = normalCount;
        this.eggCount        = eggCount;
        this.fallingEggCount = fallingEggCount;
        this.hasBoss         = hasBoss;
        this.chickenSpeed    = chickenSpeed;
    }

    public static LevelConfig get(int level) {
        int idx = Math.max(0, Math.min(level - 1, CONFIGS.length - 1));
        return CONFIGS[idx];
    }

    public int     getLevel()           { return level; }
    public int     getNormalCount()     { return normalCount; }
    public int     getEggCount()        { return eggCount; }
    public int     getFallingEggCount() { return fallingEggCount; }
    public boolean hasBoss()            { return hasBoss; }
    public float   getChickenSpeed()    { return chickenSpeed; }
}
