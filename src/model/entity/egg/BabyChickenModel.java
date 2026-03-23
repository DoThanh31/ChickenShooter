package model.entity.egg;

import model.entity.chicken.ChickenModel;

/**
 * BabyChickenModel - Gà con nở từ trứng
 * HP: 1 | Nhỏ | Di chuyển nhanh ngang | Bắn lên player
 * ke thua tu ChickenModel
 */

public class BabyChickenModel extends ChickenModel {

    public static final int WIDTH  = 24;
    public static final int HEIGHT = 22;

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
