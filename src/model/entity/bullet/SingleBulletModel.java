package model.entity.bullet;

public class SingleBulletModel extends BulletModel {

    // Tiếp tục tăng kích thước đạn
    public static final int WIDTH  = 16; // Tăng từ 12 lên 16
    public static final int HEIGHT = 32; // Tăng từ 24 lên 32

    public SingleBulletModel(float x, float y, int damage, boolean pierce) {
        super(x, y, WIDTH, HEIGHT,
                0f, -6f, // Giảm tốc độ bay từ -10 xuống -6
                damage,
                Owner.PLAYER,
                pierce);
    }

    @Override
    public void updatePos() {
        y += speedY;
    }
}
