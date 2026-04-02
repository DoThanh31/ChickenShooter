package model.entity.chicken;

public class EggChickenModel extends model.entity.chicken.ChickenModel {

    public static final int WIDTH  = 44;
    public static final int HEIGHT = 40;

    private int   eggTimer;      // Ä‘áº¿m thá»i gian tháº£ trá»©ng
    private int   eggDelay;      // frames giá»¯a 2 láº§n tháº£
    private float sinOffset;     // offset Ä‘á»ƒ bay sin wave (radians)

    public EggChickenModel(float x, float y) {
        super(x, y,
                WIDTH, HEIGHT,
                3,        // maxHp
                1.2f,     // speed
                250,      // scoreValue
                120);     // shootDelay

        this.eggDelay  = 180; // tháº£ trá»©ng má»—i 3s (60fps)
        this.eggTimer  = eggDelay;
        this.sinOffset = 0f;
    }

    @Override
    public void move() {
        x += speed * moveDir;
        sinOffset += 0.05f;
        y += (float) Math.sin(sinOffset) * 1.2f;
    }

    @Override
    public boolean canShoot() {
        return shootTimer <= 0;
    }

    public void tickEgg() {
        if (eggTimer > 0) eggTimer--;
    }

    public boolean canDropEgg() {
        return eggTimer <= 0;
    }

    public void resetEggTimer() {
        eggTimer = eggDelay;
    }


    public int   getEggTimer()  { return eggTimer; }
    public float getSinOffset() { return sinOffset; }

    @Override
    public void reset() {
        super.reset();
        eggTimer  = eggDelay;
        sinOffset = 0f;
    }
}

