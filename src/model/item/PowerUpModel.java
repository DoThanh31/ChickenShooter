package model.item;
import model.entity.PlayerModel;

/**
 * PowerUpModel - Item buff tạm thời cho player
 * ke thua itemmodel
 */
public class PowerUpModel extends ItemModel {
    private String effectType;
    private int duration;

    // Constructor phải khớp với ItemModel (x, y, w, h, lifetime)
    public PowerUpModel(float x, float y, int w, int h, int lifetime, String effectType, int duration) {
        super(x, y, w, h, lifetime);
        this.effectType = effectType;
        this.duration = duration;
    }

    // BẮT BUỘC phải override hàm này từ lớp cha ItemModel
    @Override
    public void applyEffect(PlayerModel player) {
        // Viết logic buff cho player ở đây
        // Ví dụ: if (effectType.equals("speed")) player.setSpeed(10);
    }

    // Getter và Setter
    public String getEffectType() { return effectType; }
    public void setEffectType(String effectType) { this.effectType = effectType; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
}
