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

        boss.tickSkills();

        float bx = boss.getCenterX();
        float by = boss.getY() + boss.getH();

        // 1. Skill: Bắn tỏa đạn chùm (Nhiều đạn hơn)
        if (boss.canSpread()) {
            performClusterShot(bx, by);
            boss.resetSpread();
        }

        // 2. Skill: Laser (Hiện tại dùng Laser Shot nhanh và nhiều chỗ)
        if (boss.canLaser()) {
            performMultiLaserShot(bx, by);
            boss.resetLaser();
        }

        // 3. Skill: Summon (Triệu hồi)
        if (boss.canSummon()) {
            performSummonShot(bx, by);
            boss.resetSummon();
        }

        // 4. Skill: Shield
        if (boss.canShieldSkill()) {
            boss.activateShield(300); // 5 giây
        }
    }

    private void performClusterShot(float bx, float by) {
        // Bắn chùm đạn tỏa đều 
        int bulletCount = (boss.getPhase() == BossChickenModel.Phase.ONE) ? 5 : 8;
        float startSpeedX = -4.0f;
        float stepX = 8.0f / (bulletCount - 1);

        for (int i = 0; i < bulletCount; i++) {
            bullets.add(ChickenBulletModel.angled(bx, by, startSpeedX + (i * stepX), 4f));
        }
    }

    private void performMultiLaserShot(float bx, float by) {
        // Bắn đạn laser nhanh tại 3 điểm khác nhau của Boss
        bullets.add(ChickenBulletModel.angled(bx - 40, by, 0f, 12f));
        bullets.add(ChickenBulletModel.angled(bx, by, 0f, 15f)); // Viên ở giữa nhanh nhất
        bullets.add(ChickenBulletModel.angled(bx + 40, by, 0f, 12f));
        
        // Nếu ở Phase 3, bắn thêm 2 tia chéo nhanh
        if (boss.getPhase() == BossChickenModel.Phase.THREE) {
            bullets.add(ChickenBulletModel.angled(bx - 50, by, -3f, 10f));
            bullets.add(ChickenBulletModel.angled(bx + 50, by, 3f, 10f));
        }
    }

    private void performSummonShot(float bx, float by) {
        // Bắn trứng đặc biệt (sẽ nở ra gà con khi vỡ hoặc chạm đích)
        // Hiện tại dùng đạn gà để đại diện cho việc "thả trứng"
        bullets.add(ChickenBulletModel.angled(bx - 30, by, -1.5f, 3f));
        bullets.add(ChickenBulletModel.angled(bx + 30, by, 1.5f, 3f));
    }

    public float getCooldownProgress() {
        if (boss == null) return 0f;
        return 1.0f - (float) boss.getSpreadTimer() / 120.0f;
    }

    public BossChickenModel getBoss() {
        return boss;
    }
}
