package controller.entity.egg;

import controller.entity.chicken.ChickenController;
import model.entity.egg.BabyChickenModel;

/**
 * BabyChickenController - Điều khiển logic cho gà con
 */
public class BabyChickenController extends ChickenController {

    public BabyChickenController(BabyChickenModel model) {
        super(model);
    }

    @Override
    protected void move() {
        model.move();
        
        // Gà con cũng di chuyển ngang và đổi chiều khi chạm biên
        if (model.getX() <= 0 || model.getX() + model.getW() >= 800) {
            model.reverseDir();
        }
    }

    @Override
    protected void shoot() {
        // Gà con có thể bắn đạn (nếu muốn)
        model.tickShoot();
        // Logic bắn đạn của gà con sẽ được xử lý ở GameController
    }
}
