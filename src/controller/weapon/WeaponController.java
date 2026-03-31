package controller.weapon;

import model.entity.PlayerModel;
import model.entity.bullet.BulletModel;
import model.entity.bullet.DoubleBulletModel;
import model.entity.bullet.SingleBulletModel;
import model.weapon.WeaponType;

import java.util.List;

public class WeaponController {

    private final PlayerModel player;

    public WeaponController(PlayerModel player) {
        this.player = player;
    }

    public void update() {
        player.tick();
    }

    public void fire(List<BulletModel> bullets) {
        if (!player.isAlive() || !player.canShoot()) return;

        WeaponType type = player.getWeapon().getType();
        int damage = player.getWeapon().getDamage();
        boolean pierce = player.getWeapon().isPierce();
        float px = player.getCenterX();
        float py = player.getY();

        if (type == WeaponType.SINGLE) {
            bullets.add(new SingleBulletModel(px - SingleBulletModel.WIDTH / 2f, py, damage, pierce));
        } else {
            bullets.add(new DoubleBulletModel(px - 10, py, damage, pierce, DoubleBulletModel.Side.LEFT));
            bullets.add(new DoubleBulletModel(px + 4, py, damage, pierce, DoubleBulletModel.Side.RIGHT));
        }
        player.resetShootTimer();
    }
}
