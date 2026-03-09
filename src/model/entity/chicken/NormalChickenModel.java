package model.entity.chicken;


public class NormalChickenModel extends model.entity.chicken.ChickenModel {

    public static final int WIDTH  = 40;
    public static final int HEIGHT = 36;

    public NormalChickenModel(float x, float y) {
        super(x, y,
                WIDTH, HEIGHT,
                1,
                1.5f,
                100,
                90);
    }

    @Override
    public void move() {
        x += speed * moveDir;
    }

    @Override
    public boolean canShoot() {
        return shootTimer <= 0;
    }
}
