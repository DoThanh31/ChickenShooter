package model.item;

import model.entity.PlayerModel;

/**
 * PowerUpModel - Item buff tạm thời cho player
 */
public class PowerUpModel extends ItemModel {

    public static final int WIDTH  = 24;
    public static final int HEIGHT = 24;

    public enum PowerUpType { HEAL, SHIELD, DAMAGE_UP, SCORE_DRUMSTICK }

    private PowerUpType powerUpType;

    public PowerUpModel(float x, float y, PowerUpType powerUpType) {
        super(x, y, WIDTH, HEIGHT, 300);
        this.powerUpType = powerUpType;
    }

    @Override
    public void applyEffect(PlayerModel player) {
        switch (powerUpType) {
            case HEAL      -> player.heal(1);                  // hồi 1 HP
            case SHIELD    -> player.activateShield(300);      // khiên 5s
            case DAMAGE_UP -> player.getWeapon().levelUp();    // tăng damage
            case SCORE_DRUMSTICK -> { /* Logic score sẽ được xử lý tại GameController khi nhặt */ }
        }
    }

    public PowerUpType getPowerUpType() { return powerUpType; }
}
