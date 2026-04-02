package model.entity.bullet;

public class DoubleBulletModel extends BulletModel {

    public static final int WIDTH  = 16;
    public static final int HEIGHT = 32;

    public enum Side { LEFT, RIGHT }

    private Side side;

    public DoubleBulletModel(float x, float y, int damage, boolean pierce, Side side) {
        super(x, y, WIDTH, HEIGHT,
                0f, -6f, // Giáº£m tá»‘c Ä‘á»™ bay tá»« -10 xuá»‘ng -6
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
