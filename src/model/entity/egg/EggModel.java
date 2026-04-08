package model.entity.egg;

import model.entity.EntityModel;


public class EggModel extends EntityModel {

    public static final int WIDTH  = 22;
    public static final int HEIGHT = 28;
    public static final float FALL_SPEED = 2.5f;

    private int   hatchTimer;   // frames còn lại để nở
    private int   hatchDelay;   // tổng frames cần để nở
    private int   crackStage;   // 0 -> 3

    public EggModel(float x, float y) {
        super(x, y, WIDTH, HEIGHT, 1);
        this.hatchDelay  = 180; // 3s ở 60fps
        this.hatchTimer  = hatchDelay;
        this.crackStage  = 0;
    }

    public void fall() {
        y += FALL_SPEED;
    }

    public void tickHatch() {
        if (hatchTimer > 0) {
            hatchTimer--;
            float ratio = 1f - (float) hatchTimer / hatchDelay;
            crackStage = (int)(ratio * 3); // 0, 1, 2
        }
    }

    public boolean isReadyToHatch() { return hatchTimer <= 0; }

    public boolean isOutOfBounds(int canvasH) { return y > canvasH; }

    @Override
    public void reset() {
        hp         = maxHp;
        alive      = true;
        hatchTimer = hatchDelay;
        crackStage = 0;
    }

    public int getCrackStage() { return crackStage; }
    public int getHatchTimer() { return hatchTimer; }
}

