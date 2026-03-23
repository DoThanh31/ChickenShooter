package model.entity.bullet;
/**
 * DoubleBulletModel - Đạn đôi của player
 * 2 viên song song, dùng offset để tạo viên trái/phải
 * Class thường extends BulletModel
 */
public class DoubleBulletModel extends BulletModel {

    public static final int WIDTH  = 6;
    public static final int HEIGHT = 14;

    public enum Side { LEFT, RIGHT }

    private Side side;

    public DoubleBulletModel(float x, float y, int damage, boolean pierce, Side side) {
        super(x, y, WIDTH, HEIGHT,
                0f, -8f,
                damage,
                Owner.PLAYER,
                pierce);
        this.side = side;
    }

    @Override
    public void updatePos() {
        y += speedY;
    }

    public Side getSide() { return side; }
}
