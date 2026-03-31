package controller.skill;

import model.entity.bullet.BulletModel;
import model.entity.bullet.ChickenBulletModel;
import model.entity.chicken.BossChickenModel;
import model.skill.LaserSkillModel;

import java.util.List;

public class SkillController {

    private final LaserSkillModel laserSkill;

    public SkillController() {
        this.laserSkill = new LaserSkillModel();
    }

    public void updateBossSkills(BossChickenModel boss, List<BulletModel> bullets) {
        if (!boss.isAlive()) {
            laserSkill.setActive(false);
            return;
        }
        
        // Luôn tick kỹ năng để giảm cooldown
        boss.tickSkills();

        float bx = boss.getCenterX();
        float by = boss.getY() + boss.getH();

        // 1. Skill Spread Shot (Bắn tỏa)
        if (boss.canSpread()) {
            if (bullets != null) {
                bullets.add(ChickenBulletModel.angled(bx, by, -2f, 4f));
                bullets.add(ChickenBulletModel.angled(bx, by, 0f, 4f));
                bullets.add(ChickenBulletModel.angled(bx, by, 2f, 4f));
            }
            boss.resetSpread();
        }

        // 2. Skill Laser (Tia laser ngang quét qua màn hình)
        if (boss.canLaser() && !laserSkill.isActive()) {
            laserSkill.activate();
            laserSkill.setTargetY(boss.getY() + boss.getH() + 20);
            boss.resetLaser();
        }

        // Cập nhật trạng thái laser
        if (laserSkill.isActive()) {
            laserSkill.update();
            // Nếu muốn laser đi theo Boss (vị trí Y của boss)
            laserSkill.setTargetY(boss.getY() + boss.getH() + 20);
        }

        // 3. Skill Shield (Boss bật khiên)
        if (boss.canShieldSkill()) {
            boss.activateShield(300); // 5s
        }
    }

    public LaserSkillModel getLaserSkill() {
        return laserSkill;
    }
}
