package model.entity.egg;

import model.entity.chicken.ChickenModel;

/**
 * BabyChickenModel - Gà con nở từ trứng
 * Đã tăng kích thước để dễ nhìn hơn
 */
public class BabyChickenModel extends ChickenModel {

    // Tăng kích thước gà con từ (24x22) lên (40x36)
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
