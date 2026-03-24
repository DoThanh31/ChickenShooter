package controller.entity.bullet;

import controller.Updatable;
import model.entity.bullet.BulletModel;

public class BulletController implements Updatable {

    private final BulletModel model;

    public BulletController(BulletModel model) {
        this.model = model;
    }

    @Override
    public void update() {
        if (!model.isAlive()) return;
        
        model.updatePos();
    }

    public BulletModel getModel() {
        return model;
    }
}