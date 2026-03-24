package controller.entity.egg;

import controller.entity.chicken.ChickenController;
import model.entity.egg.BabyChickenModel;

public class BabyChickenController extends ChickenController {

    public BabyChickenController(BabyChickenModel model) {
        super(model);
    }

    @Override
    protected void move() {
        model.move();

        // Gà con (BabyChicken) di chuyển ngang rất nhanh và có thể đảo chiều khi chạm viền.
        // Giả sử màn hình có width là 800
        if (model.getX() <= 0 || model.getX() + model.getW() >= 800) {
            model.reverseDir();
        }
    }

    @Override
    protected void shoot() {
        model.tickShoot();
    }
}