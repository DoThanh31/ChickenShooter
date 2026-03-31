package model.entity.bullet;

public class DoubleBulletModel extends BulletModel {

    // Tiếp tục tăng kích thước đạn
    public static final int WIDTH  = 16; // Tăng từ 12 lên 16
    public static final int HEIGHT = 32; // Tăng từ 24 lên 32

    public enum Side { LEFT, RIGHT }

    private Side side;

    public DoubleBulletModel(float x, float y, int damage, boolean pierce, Side side) {
        super(x, y, WIDTH, HEIGHT,
                0f, -6f, // Giảm tốc độ bay từ -10 xuống -6
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
