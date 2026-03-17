package model.entity.bullet;

public class BulletFactory {

    public static PlayerBullet playerBullet(float cx, float cy) {
        return new PlayerBullet(cx - 4, cy);
    }


    public static PierceBullet pierceBullet(float cx, float cy) {
        return new PierceBullet(cx - 3, cy);
    }

 
    public static PlayerBullet[] tripleShot(float cx, float cy) {
        return new PlayerBullet[]{
            new PlayerBullet(cx - 20, cy + 5),
            new PlayerBullet(cx - 4,  cy),
            new PlayerBullet(cx + 12, cy + 5)
        };
    }

    public static ChickenBullet chickenBullet(float cx, float cy) {
        return new ChickenBullet(cx - 4, cy);
    }


    public static BossBullet bossBullet(float bx, float by,
                                        float tx, float ty) {
        return new BossBullet(bx, by, tx, ty);
    }


    public static BossSpreadBullet[] bossSpread8(float bx, float by) {
        BossSpreadBullet[] bullets = new BossSpreadBullet[8];
        for (int i = 0; i < 8; i++) {
            bullets[i] = new BossSpreadBullet(bx, by, i * 45f);
        }
        return bullets;
    }

    public static BossSpreadBullet[] bossFan5(float bx, float by,
                                              float tx, float ty) {
        float baseAngle = (float) Math.toDegrees(
                Math.atan2(ty - by, tx - bx));
        BossSpreadBullet[] bullets = new BossSpreadBullet[5];
        for (int i = 0; i < 5; i++) {
            bullets[i] = new BossSpreadBullet(bx, by, baseAngle - 40 + i * 20f);
        }
        return bullets;
    }
}
