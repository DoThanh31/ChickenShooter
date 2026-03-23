package model.entity.bullet;
/**
 * SingleBulletModel - Đạn đơn của player
 * 1 viên bắn thẳng lên
 * Class thường extends BulletModel
 */
public class SingleBulletModel extends BulletModel {

    public static final int WIDTH  = 6;
    public static final int HEIGHT = 14;

    public SingleBulletModel(float x, float y, int damage, boolean pierce) {
        super(x, y, WIDTH, HEIGHT,
                0f, -8f,       // chỉ di chuyển lên (speedY âm)
                damage,
                Owner.PLAYER,
                pierce);
    }

    @Override
    public void updatePos() {
        y += speedY;
    }
}
