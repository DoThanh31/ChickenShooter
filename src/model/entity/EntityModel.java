package model.entity;

public abstract class EntityModel {
    protected float x,y;
    protected int w,h;
    protected int hp;
    protected int maxHp;
    protected boolean alive;

    public EntityModel(float x, float y, int w, int h, int hp, int maxHp, boolean alive) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.hp = hp;
        this.maxHp = maxHp;
        this.alive = alive;
    }


    //toa do tam
    public float getCenterX(){
        return x+w/2f;
    }
    public float getCenterY(){
        return y+h/2f;
    }
    //reset
    public abstract void reset();
    //hoi mau
    public void heal(int amount){
        hp=Math.min(hp+amount,maxHp);
    }
    //trung dan
    public void takeDamage(int damage) {
        if (!alive) return;
        hp -= damage;
        if (hp <= 0) {
            hp    = 0;
            alive = false;
        }
    }
    //luong mau con lai
    public float getHpRatio() {
        return (float) hp / maxHp;
    }
    //kiemtra va cham

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getHp() {
        return hp;
    }

    public int getH() {
        return h;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
