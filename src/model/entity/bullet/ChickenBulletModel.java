package model.entity.bullet;

/**
 * ChickenBulletModel - Đạn của gà / boss bắn xuống
 * Đã được tăng kích thước để nhìn rõ hơn
 */
public class ChickenBulletModel extends BulletModel {

    // Tăng kích thước đạn gà lên gấp đôi (từ 8 lên 16)
    public static final int WIDTH  = 24;
    public static final int HEIGHT = 24;

    public ChickenBulletModel(float x, float y, float speedX, float speedY, Owner owner) {
        super(x, y, WIDTH, HEIGHT,
                speedX, speedY,
                1,        // damage
                owner,
                false);   
    }

    /** Đạn thẳng xuống từ gà thường / gà trứng */
    public static ChickenBulletModel straight(float x, float y) {
        return new ChickenBulletModel(x, y, 0f, 5f, Owner.CHICKEN);
    }

    /** Đạn góc cho boss spread shot */
    public static ChickenBulletModel angled(float x, float y, float sx, float sy) {
        return new ChickenBulletModel(x, y, sx, sy, Owner.BOSS);
    }

    @Override
    public void updatePos() {
        x += speedX;
        y += speedY;
    }
}
