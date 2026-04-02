package model.entity.chicken;

/**
 * BossChickenModel - Boss mạnh mẽ với nhiều kỹ năng
 */
public class BossChickenModel extends model.entity.chicken.ChickenModel {

    public static final int WIDTH  = 120; // Tăng kích thước
    public static final int HEIGHT = 110;

    public enum Phase { ONE, TWO, THREE }

    private Phase phase;

    private int spreadCooldown;
    private int laserCooldown;
    private int summonCooldown;
    private int shieldCooldown;

    private int spreadTimer;
    private int laserTimer;
    private int summonTimer;
    private int shieldTimer;

    private boolean shieldActive;
    private int     shieldDuration;

    public BossChickenModel(float x, float y, int level) {
        super(x, y,
                WIDTH, HEIGHT,
                calcHp(level),   // HP đã trâu hơn
                0.8f,            // speed cơ bản chậm lại tí cho uy lực
                10000,           // scoreValue
                50);             // shootDelay

        this.phase = Phase.ONE;
        initSkillCooldowns();
    }

    private static int calcHp(int level) {
        // Tăng máu cho Boss (Trâu hơn đáng kể)
        return switch (level) {
            case 3  -> 200; // Tăng từ 50 lên 200
            case 4  -> 350; // Tăng từ 80 lên 350
            case 5  -> 500; // Tăng lên 500
            default -> 600; 
        };
    }

    private void initSkillCooldowns() {
        spreadCooldown = 120;  // 2s (Nhanh hơn)
        laserCooldown  = 240;  // 4s 
        summonCooldown = 400;  // 6.5s
        shieldCooldown = 480;  // 8s

        spreadTimer = spreadCooldown;
        laserTimer  = laserCooldown;
        summonTimer = summonCooldown;
        shieldTimer = shieldCooldown;

        shieldActive   = false;
        shieldDuration = 0;
    }

    @Override
    public void move() {
        x += speed * moveDir;
        // Dao động nhẹ theo trục Y
        y += (float) Math.sin(System.currentTimeMillis() / 500.0) * 0.5f;
    }

    @Override
    public boolean canShoot() {
        return shootTimer <= 0;
    }

    public void updatePhase() {
        float ratio = getHpRatio();
        if      (ratio > 0.6f) phase = Phase.ONE;
        else if (ratio > 0.3f) phase = Phase.TWO;
        else                   phase = Phase.THREE;

        speed = switch (phase) {
            case ONE   -> 0.8f;
            case TWO   -> 1.5f;
            case THREE -> 2.2f;
        };
    }

    public void tickSkills() {
        if (spreadTimer > 0) spreadTimer--;
        if (laserTimer  > 0) laserTimer--;
        if (summonTimer > 0) summonTimer--;
        if (shieldTimer > 0) shieldTimer--;
        if (shieldDuration > 0) {
            shieldDuration--;
            if (shieldDuration <= 0) shieldActive = false;
        }
    }

    public boolean canSpread() { return spreadTimer <= 0; }
    public boolean canLaser()  { return laserTimer  <= 0; } // Luôn dùng laser ở mọi phase
    public boolean canSummon() { return summonTimer <= 0; }
    public boolean canShieldSkill() {
        return shieldTimer <= 0 && !shieldActive && (phase == Phase.TWO || phase == Phase.THREE);
    }

    public void resetSpread() { spreadTimer = spreadCooldown; }
    public void resetLaser()  { laserTimer  = laserCooldown;  }
    public void resetSummon() { summonTimer = summonCooldown; }
    
    public void activateShield(int frames) {
        shieldActive   = true;
        shieldDuration = frames;
        shieldTimer    = shieldCooldown;
    }

    @Override
    public void takeDamage(int damage) {
        if (shieldActive) return;
        super.takeDamage(damage);
        updatePhase();
    }

    @Override
    public void reset() {
        super.reset();
        phase = Phase.ONE;
        initSkillCooldowns();
    }

    public Phase   getPhase()         { return phase; }
    public boolean isShieldActive()   { return shieldActive; }
    public int     getSpreadTimer()   { return spreadTimer; }
    public int     getLaserTimer()    { return laserTimer; }
    public int     getSummonTimer()   { return summonTimer; }
    public int     getShieldTimer()   { return shieldTimer; }
}
