package controller.entity.chicken;

import controller.Updatable;
import model.entity.chicken.ChickenModel;

public abstract class ChickenController implements Updatable {

    protected ChickenModel model;

    public ChickenController(ChickenModel model) {
        this.model = model;
    }

    @Override
    public void update() {
        if (!model.isAlive()) return;
        
        move();
        shoot();
    }

    protected abstract void move();
    
    protected abstract void shoot();

    public ChickenModel getModel() {
        return model;
    }
}
