package model.entity.bullet;

public class PlayerBullet extends BulletModel {

    public PlayerBullet(float x, float y) {
        super(x, y, 8, 20, 0, -16f, 10, Owner.PLAYER, false);
    }

    @Override
    public void updatePos() {
        y += speedY;
    }
}
