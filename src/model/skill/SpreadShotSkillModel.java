package model.skill;
public class SpreadShotSkillModel extends SkillModel {

    private int bulletCount; // số viên đạn (8 hoặc 12 ở phase 3)

    public SpreadShotSkillModel() {
        super("SPREAD", 180, 1); // cooldown 3s, kích hoạt 1 frame (instant)
        this.bulletCount = 8;
    }

    @Override
    public void activate() {
        active        = true;
        durationTimer = duration;
    }

    @Override
    public void update() { tick(); }

    public void setBulletCount(int count) { this.bulletCount = count; }
    public int  getBulletCount()          { return bulletCount; }
}
