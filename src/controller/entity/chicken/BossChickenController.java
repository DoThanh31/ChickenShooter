package controller.entity.chicken;

import model.entity.chicken.BossChickenModel;

public class BossChickenController extends ChickenController {

    public BossChickenController(BossChickenModel model) {
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
        ((BossChickenModel) model).tickSkills();
    }
}
