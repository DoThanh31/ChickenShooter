package model.entity.egg;
/**
 * BabyChickenModel - Gà con nở từ trứng
 * HP: 1 | Nhỏ | Di chuyển nhanh ngang | Bắn lên player
 * ke thua tu ChickenModel
 */
import model.entity.chicken.ChickenModel;

public class BabyChickenModel extends ChickenModel {

    private int hp = 1;

    // tốc độ di chuyển ngang (nhanh)
    private double speedX = 4;

    // hướng di chuyển
    private int direction = 1;

    // hệ thống bắn
    private int shootCooldown = 0;
    private int shootDelay = 100;

    public BabyChickenModel(double x, double y) {
        super(x, y);

        // gà con nhỏ hơn
        this.width = 20;
        this.height = 20;
    }

    @Override
    public void update() {

        // di chuyển ngang
        x += speedX * direction;

        // đổi hướng khi chạm biên màn hình
        if (x <= 0 || x + width >= 800) {
            direction *= -1;
        }

        // đếm thời gian bắn
        shootCooldown++;

        if (shootCooldown >= shootDelay) {
            shoot();
            shootCooldown = 0;
        }
    }

    // bắn đạn về phía player
    private void shoot() {
        createBullet(x + width / 2, y + height);
    }

    // nhận damage
    @Override
    public void takeDamage(int damage) {
        hp -= damage;

        if (hp <= 0) {
            destroy();
        }
    }

    public int getHp() {
        return hp;
    }
}
public class BabyChickenModel {
}
