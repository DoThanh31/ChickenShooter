package controller.entity.chicken;

import controller.LevelController;
import model.entity.chicken.EggChickenModel;

public class EggChickenController extends ChickenController {

    private final LevelController levelController; // Cần LevelController để thêm trứng

    public EggChickenController(EggChickenModel model, LevelController levelController) {
        super(model);
        this.levelController = levelController;
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
        EggChickenModel eggChicken = (EggChickenModel) model;
        eggChicken.tickEgg();

        if (eggChicken.canDropEgg()) {
            levelController.addDroppedEgg(eggChicken.getCenterX(), eggChicken.getY() + eggChicken.getH());
            eggChicken.resetEggTimer();
        }
    }
}
