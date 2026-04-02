package controller.skill;

import model.entity.bullet.ChickenBulletModel;
import model.entity.chicken.BossChickenModel;
import model.entity.chicken.NormalChickenModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkillController {

    private static final int GAME_WIDTH = 800;

    private final Random random;

    public SkillController() {
        this.random = new Random();
    }

    public BossAttackResult updateBossSkills(BossChickenModel boss) {
        List<ChickenBulletModel> bullets = new ArrayList<>();
        List<NormalChickenModel> summons = new ArrayList<>();

        if (!boss.isAlive()) {
            return new BossAttackResult(bullets, summons);
        }

        boss.tickSkills();

        float bx = boss.getCenterX();
        float by = boss.getY() + boss.getH();

        if (boss.canSpread()) {
            bullets.add(ChickenBulletModel.angled(bx, by, -3f, 4f));
            bullets.add(ChickenBulletModel.angled(bx, by, -1.5f, 4.5f));
            bullets.add(ChickenBulletModel.angled(bx, by, 0f, 5f));
            bullets.add(ChickenBulletModel.angled(bx, by, 1.5f, 4.5f));
            bullets.add(ChickenBulletModel.angled(bx, by, 3f, 4f));
            boss.resetSpread();
        }

        if (boss.canSummon()) {
            for (int i = 0; i < 2; i++) {
                float offsetX = random.nextInt(121) - 60;
                float summonX = Math.max(0, Math.min(GAME_WIDTH - NormalChickenModel.WIDTH, bx + offsetX));
                float summonY = Math.min(220, by + 20 + (i * 28));
                NormalChickenModel summon = new NormalChickenModel(summonX, summonY);
                summon.setSpeed(1.8f + random.nextFloat() * 0.7f);
                summons.add(summon);
            }
            boss.resetSummon();
        }

        if (boss.canShieldSkill()) {
            boss.activateShield(300);
        }

        return new BossAttackResult(bullets, summons);
    }

    public record BossAttackResult(List<ChickenBulletModel> bullets, List<NormalChickenModel> summons) {
    }
}
