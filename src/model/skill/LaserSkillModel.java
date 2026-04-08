package model.skill;
public class LaserSkillModel extends SkillModel {

    private float targetY; // vị trí Y tia laser (ngang)

    public LaserSkillModel() {
        super("LASER", 300, 90); // cooldown 5s, hoạt động 1.5s
    }

    @Override
    public void activate() {
        active        = true;
        durationTimer = duration;
    }

    @Override
    public void update() { tick(); }

    public void setTargetY(float y) { this.targetY = y; }
    public float getTargetY()       { return targetY; }
}
