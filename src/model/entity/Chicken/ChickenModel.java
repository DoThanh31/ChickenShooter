package model.entity.Chicken;

import model.entity.EntityModel;

public abstract class ChickenModel extends EntityModel {

    protected float speed;
    protected int   moveDir;
    protected int   scoreValue;
    protected int   shootTimer;
    protected int   shootDelay;

    public ChickenModel(float x, float y, int w, int h, int hp, int maxHp, boolean alive, float speed, int scoreValue, int moveDir, int shootTimer, int shootDelay) {
        super(x, y, w, h, hp, maxHp, alive);
        this.speed = speed;
        this.scoreValue = scoreValue;
        this.moveDir = 1;
        this.shootTimer = shootDelay;//dan da san sang
        this.shootDelay = shootDelay;
    }

    public abstract void move();

    public abstract boolean canShoot();


    public void tickShoot() {
        if (shootTimer > 0) shootTimer--;
    }

    public void resetShootTimer() {
        shootTimer = shootDelay;
    }

    public void reverseDir() {
        moveDir = -moveDir;
    }

    @Override
    public void reset() {
        hp         = maxHp;
        alive      = true;
        moveDir    = 1;
        shootTimer = shootDelay;
    }


    public float getSpeed()      { return speed; }
    public int   getMoveDir()    { return moveDir; }
    public int   getScoreValue() { return scoreValue; }
    public int   getShootTimer() { return shootTimer; }

    public void setSpeed(float speed) { this.speed = speed; }
}
