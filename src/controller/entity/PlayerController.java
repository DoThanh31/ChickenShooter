package controller.entity;

import controller.Updatable;
import model.entity.PlayerModel;

public class PlayerController implements Updatable {

    private final PlayerModel model;
    
    private static final int GAME_WIDTH  = 800;
    private static final int GAME_HEIGHT = 600;

    public PlayerController(PlayerModel model) {
        this.model = model;
    }

    @Override
    public void update() {
        if (!model.isAlive()) return;

        model.tick();
        
        limitMovement();
    }
    
    private void limitMovement() {
        if (model.getX() < 0) model.setX(0);
        if (model.getX() + model.getW() > GAME_WIDTH) model.setX(GAME_WIDTH - model.getW());
        if (model.getY() < 0) model.setY(0);
        if (model.getY() + model.getH() > GAME_HEIGHT) model.setY(GAME_HEIGHT - model.getH());
    }

    public PlayerModel getModel() {
        return model;
    }
}
