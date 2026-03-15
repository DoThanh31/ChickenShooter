package model;


public class LevelConfig {

    private final int   level;
    private final int   normalCount;
    private final int   eggCount;
    private final boolean hasBoss;
    private final float chickenSpeed;

    // Cấu hình cố định cho từng level
    private static final LevelConfig[] CONFIGS = {
            new LevelConfig(1,  8, 0, false, 1.0f),
            new LevelConfig(2, 10, 0, false, 1.3f),
            new LevelConfig(3,  8, 3, false,  1.5f),
            new LevelConfig(4,  6, 4, false,  1.8f),
            new LevelConfig(5,  4, 4, true,  2.0f),
    };

    private LevelConfig(int level, int normalCount, int eggCount,
                        boolean hasBoss, float chickenSpeed) {
        this.level        = level;
        this.normalCount  = normalCount;
        this.eggCount     = eggCount;
        this.hasBoss      = hasBoss;
        this.chickenSpeed = chickenSpeed;
    }

    public static LevelConfig get(int level) {
        int idx = Math.max(0, Math.min(level - 1, CONFIGS.length - 1));
        return CONFIGS[idx];
    }

    // ── Getter ────────────────────────────────────────────────
    public int     getLevel()        { return level; }
    public int     getNormalCount()  { return normalCount; }
    public int     getEggCount()     { return eggCount; }
    public boolean hasBoss()         { return hasBoss; }
    public float   getChickenSpeed() { return chickenSpeed; }
}
