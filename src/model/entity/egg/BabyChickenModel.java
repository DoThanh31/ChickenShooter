package model.entity.egg;
/**
 * BabyChickenModel - Gà con nở từ trứng
 * HP: 1 | Nhỏ | Di chuyển nhanh ngang | Bắn lên player
 * ke thua tu ChickenModel
 */
import model.entity.chicken.ChickenModel;

public class BabyChickenModel extends ChickenModel {

    private int hp = 1;

    private double speedX = 4; // di chuyển nhanh ngang
    private int direction = 1; // 1: phải, -1: trái

    private int shootCooldown = 0;
    private int shootDelay = 120; // thời gian giữa mỗi lần bắn

    public BabyChickenModel(double x, double y) {
        super(x, y);
        this.width = 20;   // nhỏ
        this.height = 20;
    }

    public void update() {

        // di chuyển ngang
        x += speedX * direction;

        // đổi hướng khi chạm biên
        if (x < 0 || x > 800) {
            direction *= -1;
        }

        // xử lý bắn
        shootCooldown++;

        if (shootCooldown >= shootDelay) {
            shoot();
            shootCooldown = 0;
        }
    }

    private void shoot() {
        // bắn lên player (đạn hướng xuống player)
        createBullet(x + width / 2, y + height);
    }

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
