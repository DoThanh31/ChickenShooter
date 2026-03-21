package model.item;
import model.entity.PlayerModel;
/**
 * WeaponItemModel - Item nâng cấp vũ khí
 * Cùng loại → +1 level | Khác loại → đổi loại
 * ke thua itemmodel
 */
public class WeaponItemModel extends ItemModel {
    private String weaponType; // Ví dụ: "PISTOL", "LASER", "SHOTGUN"
    private int powerBoost;    // Chỉ số sức mạnh cộng thêm

    public WeaponItemModel(float x, float y, int w, int h, int lifetime, String weaponType, int powerBoost) {
        super(x, y, w, h, lifetime);
        this.weaponType = weaponType;
        this.powerBoost = powerBoost;
    }

    @Override
    public void applyEffect(PlayerModel player) {
        // Logic: Nếu player đang dùng loại vũ khí này thì nâng cấp, không thì đổi mới
        // Sau khi ăn xong thì vật phẩm biến mất
    }

    // Getter và Setter
    public String getWeaponType() { return weaponType; }
    public void setWeaponType(String weaponType) { this.weaponType = weaponType; }

    public int getPowerBoost() { return powerBoost; }
    public void setPowerBoost(int powerBoost) { this.powerBoost = powerBoost; }
}
