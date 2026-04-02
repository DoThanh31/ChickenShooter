package model.item;

import model.entity.EntityModel;
import model.entity.PlayerModel;

public abstract class ItemModel extends EntityModel {

    protected int lifetime;  // frames cÃ²n láº¡i trÆ°á»›c khi biáº¿n máº¥t
    protected float fallSpeed;

    public ItemModel(float x, float y, int w, int h, int lifetime) {
        super(x, y, w, h, 1);
        this.lifetime  = lifetime;
        this.fallSpeed = 1.5f;
    }

    public abstract void applyEffect(PlayerModel player);

    public void update() {
        y += fallSpeed;
        if (lifetime > 0) lifetime--;
        if (lifetime <= 0) alive = false;
    }

    public boolean isExpired()              { return lifetime <= 0; }
    public boolean isOutOfBounds(int h)     { return y > h; }

    @Override
    public void reset() { alive = false; }

    public int getLifetime() { return lifetime; }
}
