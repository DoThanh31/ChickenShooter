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
        
        if (model.getX() <= 0 || model.getX() + model.getW() >= 800) {
            model.reverseDir();
        }
    }

    @Override
    protected void shoot() {
        model.tickShoot();
    }
}
