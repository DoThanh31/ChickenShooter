package model.entity.bullet;

import model.entity.EntityModel;

public abstract class BulletModel extends EntityModel {

    public enum Owner { PLAYER, CHICKEN, BOSS }

    protected float  speedX, speedY;
    protected int    damage;
    protected Owner  owner;
    protected boolean pierce;

    public BulletModel(float x, float y, int w, int h,
                       float speedX, float speedY,
                       int damage, Owner owner, boolean pierce) {
        super(x, y, w, h, 1);
        this.speedX = speedX;
        this.speedY = speedY;
        this.damage = damage;
        this.owner  = owner;
        this.pierce = pierce;
    }

    public abstract void updatePos();

    public boolean isOutOfBounds(int canvasW, int canvasH) {
        return x + w < 0 || x > canvasW || y + h < 0 || y > canvasH;
    }

    @Override
    public void reset() { alive = false; }

    public int    getDamage() { return damage; }
    public Owner  getOwner()  { return owner; }
    public boolean isPierce() { return pierce; }
    public float  getSpeedX() { return speedX; }
    public float  getSpeedY() { return speedY; }
}
