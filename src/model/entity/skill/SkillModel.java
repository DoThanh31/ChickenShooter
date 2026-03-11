package model.entity.skill;

public abstract class SkillModel {

    protected String  name;
    protected int     cooldown;
    protected int     cooldownTimer;
    protected boolean active;
    protected int     duration;
    protected int     durationTimer;

    public SkillModel(String name, int cooldown, int duration) {
        this.name          = name;
        this.cooldown      = cooldown;
        this.cooldownTimer = cooldown;
        this.duration      = duration;
        this.durationTimer = 0;
        this.active        = false;
    }

    public abstract void activate();

    public abstract void update();

    protected void tick() {
        if (active) {
            if (durationTimer > 0) durationTimer--;
            else                   deactivate();
        } else {
            if (cooldownTimer > 0) cooldownTimer--;
        }
    }

    protected void deactivate() {
        active        = false;
        durationTimer = 0;
        cooldownTimer = cooldown;
    }

    public boolean isReady()  { return !active && cooldownTimer <= 0; }
    public boolean isActive() { return active; }
    public String  getName()  { return name; }

    public float getCooldownRatio() {
        return 1f - (float) cooldownTimer / cooldown;
    }
}

