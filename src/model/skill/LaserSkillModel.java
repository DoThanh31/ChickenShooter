package model.skill;
public class LaserSkillModel extends SkillModel {

    private float targetY; // vá»‹ trÃ­ Y tia laser (ngang)

    public LaserSkillModel() {
        super("LASER", 300, 90); // cooldown 5s, hoáº¡t Ä‘á»™ng 1.5s
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
