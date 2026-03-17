package model.entity.bullet;

public class ChickenBullet extends BulletModel {

    public ChickenBullet(float x, float y) {
        super(x, y, 8, 16, 0, 6f, 8, Owner.CHICKEN, false);
    }

    @Override
    public void updatePos() {
        y += speedY;
    }
}


public class BossBullet extends BulletModel {

    public BossBullet(float bossX, float bossY,
                      float targetX, float targetY) {
        super(bossX, bossY, 14, 14,
              computeVx(bossX, bossY, targetX, targetY),
              computeVy(bossX, bossY, targetX, targetY),
              20, Owner.BOSS, false);
    }

    private static final float SPEED = 7f;

    private static float computeVx(float bx, float by, float tx, float ty) {
        float dx = tx - bx, dy = ty - by;
        float len = (float) Math.hypot(dx, dy);
        return len == 0 ? 0 : dx / len * SPEED;
    }

    private static float computeVy(float bx, float by, float tx, float ty) {
        float dx = tx - bx, dy = ty - by;
        float len = (float) Math.hypot(dx, dy);
        return len == 0 ? SPEED : dy / len * SPEED;
    }

    @Override
    public void updatePos() {
        x += speedX;
        y += speedY;
    }
}


public class BossSpreadBullet extends BulletModel {

    private static final float SPEED = 6f;
    
    public BossSpreadBullet(float x, float y, float angleDeg) {
        super(x, y, 12, 12,
              (float)(Math.cos(Math.toRadians(angleDeg)) * SPEED),
              (float)(Math.sin(Math.toRadians(angleDeg)) * SPEED),
              15, Owner.BOSS, false);
    }

    @Override
    public void updatePos() {
        x += speedX;
        y += speedY;
    }
}