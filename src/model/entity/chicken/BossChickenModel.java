package model.entity.chicken;

public class BossChickenModel extends ChickenModel {

    public static final int WIDTH = 120;
    public static final int HEIGHT = 110;

    public enum Phase { ONE, TWO, THREE }

    private Phase phase;

    private int spreadCooldown;
    private int summonCooldown;
    private int shieldCooldown;

    private int spreadTimer;
    private int summonTimer;
    private int shieldTimer;

    private boolean shieldActive;
    private int shieldDuration;

    public BossChickenModel(float x, float y, int level) {
        super(x, y, WIDTH, HEIGHT, calcHp(level), 0.8f, 10000, 50);
        this.phase = Phase.ONE;
        initSkillCooldowns();
    }

    private static int calcHp(int level) {
        return switch (level) {
            case 3 -> 200;
            case 4 -> 350;
            case 5 -> 500;
            default -> 600;
        };
    }

    private void initSkillCooldowns() {
        spreadCooldown = 120;
        summonCooldown = 400;
        shieldCooldown = 480;

        spreadTimer = spreadCooldown;
        summonTimer = summonCooldown;
        shieldTimer = shieldCooldown;

        shieldActive = false;
        shieldDuration = 0;
    }

    @Override
    public void move() {
        x += speed * moveDir;
        y += (float) Math.sin(System.currentTimeMillis() / 500.0) * 0.5f;
    }

    @Override
    public boolean canShoot() {
        return shootTimer <= 0;
    }

    public void updatePhase() {
        float ratio = getHpRatio();
        if (ratio > 0.6f) phase = Phase.ONE;
        else if (ratio > 0.3f) phase = Phase.TWO;
        else phase = Phase.THREE;

        speed = switch (phase) {
            case ONE -> 0.8f;
            case TWO -> 1.5f;
            case THREE -> 2.2f;
        };
    }

    public void tickSkills() {
        if (spreadTimer > 0) spreadTimer--;
        if (summonTimer > 0) summonTimer--;
        if (shieldTimer > 0) shieldTimer--;
        if (shieldDuration > 0) {
            shieldDuration--;
            if (shieldDuration <= 0) shieldActive = false;
        }
    }

    public boolean canSpread() { return spreadTimer <= 0; }
    public boolean canSummon() { return summonTimer <= 0; }

    public boolean canShieldSkill() {
        return shieldTimer <= 0 && !shieldActive && (phase == Phase.TWO || phase == Phase.THREE);
    }

    public void resetSpread() { spreadTimer = spreadCooldown; }
    public void resetSummon() { summonTimer = summonCooldown; }

    public void activateShield(int frames) {
        shieldActive = true;
        shieldDuration = frames;
        shieldTimer = shieldCooldown;
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

    public Phase getPhase() { return phase; }
    public boolean isShieldActive() { return shieldActive; }
    public int getSpreadTimer() { return spreadTimer; }
    public int getSummonTimer() { return summonTimer; }
    public int getShieldTimer() { return shieldTimer; }
}
