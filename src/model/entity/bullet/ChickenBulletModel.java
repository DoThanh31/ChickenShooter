package model.entity.bullet;
/**
 * ChickenBulletModel - Đạn của gà / boss bắn xuống
 * Hỗ trợ đạn thẳng (gà thường/trứng) và đạn góc (boss spread)
 *  Class thường extends BulletModel
 */
public class ChickenBulletModel extends BulletModel {

    public static final int WIDTH  = 8;
    public static final int HEIGHT = 8;

    public ChickenBulletModel(float x, float y, float speedX, float speedY, Owner owner) {
        super(x, y, WIDTH, HEIGHT,
                speedX, speedY,
                1,        // damage
                owner,
                false);   // không xuyên
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

