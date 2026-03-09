package model.entity.chicken;

public class BossChickenModel extends model.entity.chicken.ChickenModel {

    public static final int WIDTH  = 100;
    public static final int HEIGHT = 90;

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
    private int     shieldDuration; // frames khiên còn lại

    public BossChickenModel(float x, float y, int level) {
        super(x, y,
                WIDTH, HEIGHT,
                calcHp(level),   // maxHp theo level
                1.0f,            // speed
                5000,            // scoreValue
                60);             // shootDelay

        this.phase = Phase.ONE;
        initSkillCooldowns();
    }

    private static int calcHp(int level) {
        return switch (level) {
            case 3  -> 50;
            case 4  -> 80;
            default -> 120; // level 5+
        };
    }

    private void initSkillCooldowns() {
        spreadCooldown = 180;  // 3s
        laserCooldown  = 300;  // 5s
        summonCooldown = 360;  // 6s
        shieldCooldown = 420;  // 7s

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
    }

    @Override
    public boolean canShoot() {
        return shootTimer <= 0;
    }

    // ── Phase ─────────────────────────────────────────────────

    /** Cập nhật phase dựa trên % máu hiện tại */
    public void updatePhase() {
        float ratio = getHpRatio();
        if      (ratio > 0.5f) phase = Phase.ONE;
        else if (ratio > 0.2f) phase = Phase.TWO;
        else                   phase = Phase.THREE;

        // Phase 2+ tăng tốc độ
        speed = switch (phase) {
            case ONE   -> 1.0f;
            case TWO   -> 1.8f;
            case THREE -> 2.5f;
        };
    }

    // ── Skill timers ─────────────────────────────────────────

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
    public boolean canLaser()  { return laserTimer  <= 0 && phase != Phase.ONE; }
    public boolean canSummon() { return summonTimer <= 0; }
    public boolean canShieldSkill() {
        return shieldTimer <= 0 && !shieldActive && phase == Phase.THREE;
    }

    public void resetSpread() { spreadTimer = switch(phase){
        case ONE -> spreadCooldown; case TWO -> 120; default -> 80; }; }
    public void resetLaser()  { laserTimer  = laserCooldown;  }
    public void resetSummon() { summonTimer = summonCooldown; }
    public void activateShield(int frames) {
        shieldActive   = true;
        shieldDuration = frames;
        shieldTimer    = shieldCooldown;
    }

    @Override
    public void takeDamage(int damage) {
        if (shieldActive) return; // khiên chặn damage
        super.takeDamage(damage);
        updatePhase();
    }

    @Override
    public void reset() {
        super.reset();
        phase = Phase.ONE;
        initSkillCooldowns();
    }

    // ── Getter ────────────────────────────────────────────────

    public Phase   getPhase()         { return phase; }
    public boolean isShieldActive()   { return shieldActive; }
    public int     getSpreadTimer()   { return spreadTimer; }
    public int     getLaserTimer()    { return laserTimer; }
    public int     getSummonTimer()   { return summonTimer; }
    public int     getShieldTimer()   { return shieldTimer; }
}

