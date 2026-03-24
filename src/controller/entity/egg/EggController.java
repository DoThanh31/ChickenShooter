package controller.entity.egg;

import controller.Updatable;
import model.entity.egg.EggModel;

public class EggController implements Updatable {

    private final EggModel model;

    public EggController(EggModel model) {
        this.model = model;
    }

    @Override
    public void update() {
        if (!model.isAlive()) return;

        model.fall();
        model.tickHatch();
    }

    public EggModel getModel() {
        return model;
    }
}