package model.entity.egg;

import model.entity.chicken.ChickenModel;

public class BabyChickenModel extends ChickenModel {

    public static final int WIDTH  = 40;
    public static final int HEIGHT = 36;

    public BabyChickenModel(float x, float y) {
        super(x, y,
                WIDTH, HEIGHT,
                1,       // maxHp
                3.0f,    // speed (nhanh hơn gà thường)
                150,     // scoreValue
                100);    // shootDelay
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
