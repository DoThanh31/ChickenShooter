package controller.entity.chicken;

import model.entity.chicken.NormalChickenModel;

public class NormalChickenController extends ChickenController {

    public NormalChickenController(NormalChickenModel model) {
        super(model);
    }

    @Override
    protected void move() {
        model.move();
        
        // Đảo hướng khi chạm viền màn hình (giả sử màn hình 800)
        if (model.getX() <= 0 || model.getX() + model.getW() >= 800) {
            model.reverseDir();
        }
    }

    @Override
    protected void shoot() {
        model.tickShoot();
    }
}