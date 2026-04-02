package controller.skill;

import model.entity.bullet.BulletModel;
import model.entity.bullet.ChickenBulletModel;
import model.entity.chicken.BossChickenModel;

import java.util.List;

public class BossSkillController {

    private final BossChickenModel boss;
    private final List<BulletModel> bullets;

    public BossSkillController(BossChickenModel boss, List<BulletModel> bullets) {
        this.boss = boss;
        this.bullets = bullets;
    }

    public void update() {
        if (!boss.isAlive()) return;

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

        if (boss.canShieldSkill()) {
            boss.activateShield(300);
        }
    }

    public float getCooldownProgress() {
        if (boss == null) return 0f;
        return 1.0f - (float) boss.getSpreadTimer() / 120.0f;
    }

    public BossChickenModel getBoss() {
        return boss;
    }
}
