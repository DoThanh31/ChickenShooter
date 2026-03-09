package model.entity;

public class PlayerModel extends EntityModel{
    public PlayerModel(float x, float y, int w, int h, int hp, int maxHp, boolean alive) {
        super(x, y, w, h, hp, maxHp, alive);
    }

    @Override
    public void reset() {

    }
}
