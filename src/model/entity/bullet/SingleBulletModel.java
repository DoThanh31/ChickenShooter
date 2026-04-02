package model.entity.bullet;

public class SingleBulletModel extends BulletModel {

    // Tăng kích thước đạn người chơi to hơn
    public static final int WIDTH  = 24;
    public static final int HEIGHT = 48;

    public SingleBulletModel(float x, float y, int damage, boolean pierce) {
        super(x, y, WIDTH, HEIGHT,
                0f, -6f,
                damage,
                Owner.PLAYER,
                pierce);
    }

    @Override
    public void updatePos() {
        y += speedY;
    }
}
