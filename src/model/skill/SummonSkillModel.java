package model.skill;
public class SummonSkillModel extends SkillModel {

    private int summonCount; // sá»‘ gÃ  triá»‡u há»“i má»—i láº§n

    public SummonSkillModel() {
        super("SUMMON", 360, 1); // cooldown 6s, instant
        this.summonCount = 3;
    }

    @Override
    public void activate() {
        active        = true;
        durationTimer = duration;
    }

    @Override
    public void update() { tick(); }

    public void setSummonCount(int count) { this.summonCount = count; }
    public int  getSummonCount()          { return summonCount; }
}
