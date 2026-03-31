package controller.weapon;

import model.entity.PlayerModel;
import model.item.WeaponItemModel;

public class WeaponUpgradeController {

    private final PlayerModel player;

    public WeaponUpgradeController(PlayerModel player) {
        this.player = player;
    }

    public void applyUpgrade(WeaponItemModel item) {
        if (player.getWeapon().getType() == item.getWeaponType()) {
            player.getWeapon().levelUp();
        } else {
            player.getWeapon().switchType(item.getWeaponType());
        }
    }
}
