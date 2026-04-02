package model.item;

import model.entity.PlayerModel;
import model.weapon.WeaponType;

public class WeaponItemModel extends ItemModel {

    public static final int WIDTH  = 24;
    public static final int HEIGHT = 24;

    private WeaponType weaponType; // loáº¡i Ä‘áº¡n item nÃ y chá»©a

    public WeaponItemModel(float x, float y, WeaponType weaponType) {
        super(x, y, WIDTH, HEIGHT, 300); // tá»“n táº¡i 5s (60fps)
        this.weaponType = weaponType;
    }

    @Override
    public void applyEffect(PlayerModel player) {
        if (player.getWeapon().getType() == weaponType) {
            player.getWeapon().levelUp();        // cÃ¹ng loáº¡i â†’ lÃªn level
        } else {
            player.getWeapon().switchType(weaponType); // khÃ¡c loáº¡i â†’ Ä‘á»•i
        }
    }

    public WeaponType getWeaponType() { return weaponType; }
}
