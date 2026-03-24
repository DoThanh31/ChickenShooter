package controller.entity.chicken;

import model.entity.chicken.EggChickenModel;

public class EggChickenController extends ChickenController {

    public EggChickenController(EggChickenModel model) {
        super(model);
    }

    @Override
    protected void move() {
        model.move();
        
        // Cập nhật vị trí và đảo chiều
        if (model.getX() <= 0 || model.getX() + model.getW() >= 800) {
            model.reverseDir();
        }
    }

    @Override
    protected void shoot() {
        model.tickShoot();
        ((EggChickenModel) model).tickEgg();
    }
}