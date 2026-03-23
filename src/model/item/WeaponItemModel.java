package model.item;

import model.entity.PlayerModel;
import model.weapon.WeaponType;

/**
 * WeaponItemModel - Item nâng cấp vũ khí
 * Cùng loại → +1 level | Khác loại → đổi loại
 */
public class WeaponItemModel extends ItemModel {

    public static final int WIDTH  = 24;
    public static final int HEIGHT = 24;

    private WeaponType weaponType; // loại đạn item này chứa

    public WeaponItemModel(float x, float y, WeaponType weaponType) {
        super(x, y, WIDTH, HEIGHT, 300); // tồn tại 5s (60fps)
        this.weaponType = weaponType;
    }

    @Override
    public void applyEffect(PlayerModel player) {
        if (player.getWeapon().getType() == weaponType) {
            player.getWeapon().levelUp();        // cùng loại → lên level
        } else {
            player.getWeapon().switchType(weaponType); // khác loại → đổi
        }
    }

    public WeaponType getWeaponType() { return weaponType; }
}