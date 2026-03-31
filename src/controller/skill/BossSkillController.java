package controller.skill;

import model.entity.bullet.BulletModel;
import model.entity.bullet.ChickenBulletModel;
import model.entity.chicken.BossChickenModel;

import java.util.List;
import java.util.Random;

/**
 * BossSkillController - Điều phối các kỹ năng của Boss
 */
public class BossSkillController {

    private final BossChickenModel boss;
    private final List<BulletModel> bullets;
    private final Random random;

    public BossSkillController(BossChickenModel boss, List<BulletModel> bullets) {
        this.boss = boss;
        this.bullets = bullets;
        this.random = new Random();
    }

    public void update() {
        if (!boss.isAlive()) return;

        // Cập nhật các bộ đếm kỹ năng trong model boss
        boss.tickSkills();

        float bx = boss.getCenterX();
        float by = boss.getY() + boss.getH();

        // 1. Skill: Spread Shot (Bắn tỏa)
        if (boss.canSpread()) {
            performSpreadShot(bx, by);
            boss.resetSpread();
        }

        // 2. Skill: Laser (Tia laser nhanh)
        if (boss.canLaser()) {
            performLaserShot(bx, by);
            boss.resetLaser();
        }

        // 3. Skill: Summon (Triệu hồi gà con)
        if (boss.canSummon()) {
            performSummon();
            boss.resetSummon();
        }

        // 4. Skill: Shield (Boss tự bật khiên khi đủ điều kiện - Phase THREE)
        if (boss.canShieldSkill()) {
            boss.activateShield(300); // 5 giây
        }
    }

    private void performSpreadShot(float bx, float by) {
        switch (boss.getPhase()) {
            case ONE -> {
                bullets.add(ChickenBulletModel.angled(bx, by, -2f, 4f));
                bullets.add(ChickenBulletModel.angled(bx, by, 0f, 4f));
                bullets.add(ChickenBulletModel.angled(bx, by, 2f, 4f));
            }
            case TWO -> {
                bullets.add(ChickenBulletModel.angled(bx, by, -3f, 5f));
                bullets.add(ChickenBulletModel.angled(bx, by, -1f, 5f));
                bullets.add(ChickenBulletModel.angled(bx, by, 1f, 5f));
                bullets.add(ChickenBulletModel.angled(bx, by, 3f, 5f));
            }
            case THREE -> {
                for (float sx = -5f; sx <= 5f; sx += 2.5f) {
                    bullets.add(ChickenBulletModel.angled(bx, by, sx, 4f));
                }
            }
        }
    }

    private void performLaserShot(float bx, float by) {
        // Bắn 1 loạt đạn tốc độ cao hướng thẳng vào Player
        bullets.add(ChickenBulletModel.angled(bx - 20, by, 0f, 10f));
        bullets.add(ChickenBulletModel.angled(bx, by, 0f, 12f));
        bullets.add(ChickenBulletModel.angled(bx + 20, by, 0f, 10f));
    }

    private void performSummon() {
        // Logic này có thể được mở rộng để thêm gà con vào LevelController thông qua Callback
        // Tạm thời bắn 2 quả trứng lớn (Egg) đại diện cho summon
        bullets.add(ChickenBulletModel.angled(boss.getX(), boss.getY() + 20, -1f, 3f));
        bullets.add(ChickenBulletModel.angled(boss.getX() + boss.getW(), boss.getY() + 20, 1f, 3f));
    }

    /** Trả về tỷ lệ cooldown kỹ năng Spread (để hiển thị HUD) */
    public float getCooldownProgress() {
        if (boss == null) return 0f;
        return 1.0f - (float) boss.getSpreadTimer() / 180.0f;
    }

    public BossChickenModel getBoss() {
        return boss;
    }
}
