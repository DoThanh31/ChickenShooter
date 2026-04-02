package model.entity;

import model.weapon.WeaponModel;
import model.weapon.WeaponType;

/**
 * PlayerModel - Dữ liệu người chơi
 * Tiếp tục tăng kích thước phi thuyền theo yêu cầu
 */
public class PlayerModel extends EntityModel {

    public static final int START_X    = 360;
    public static final int START_Y    = 500;
    public static final int WIDTH      = 80;  // Tăng từ 64 lên 80
    public static final int HEIGHT     = 60;  // Tăng từ 48 lên 60
    public static final int MAX_HP     = 3;
    public static final int MAX_LIVES  = 3;

    private int lives;
    private int shootTimer;
    private boolean shieldActive;
    private int shieldTimer;

    private WeaponModel weapon;

    public PlayerModel() {
        super(START_X, START_Y, WIDTH, HEIGHT, MAX_HP);
        this.lives        = MAX_LIVES;
        this.shootTimer   = 0;
        this.shieldActive = false;
        this.shieldTimer  = 0;
        this.weapon       = new WeaponModel();
    }

    public void loseLife() {
        lives--;
        if (lives > 0) {
            hp    = MAX_HP;
            alive = true;
        } else {
            alive = false;
        }
    }

    @Override
    public void takeDamage(int damage) {
        if (shieldActive) {
            shieldActive = false;
            shieldTimer  = 0;
            return;
        }
        super.takeDamage(damage);
        if (!alive && lives > 0) loseLife();

    }

    public void activateShield(int frames) {
        shieldActive = true;
        shieldTimer  = frames;
    }

    public void tick() {
        if (shootTimer > 0)  shootTimer--;
        if (shieldTimer > 0) shieldTimer--;
        else                 shieldActive = false;
    }

    public boolean canShoot() { return shootTimer <= 0; }

    public void resetShootTimer() {
        shootTimer = weapon.getCooldown();
    }

    @Override
    public void reset() {
        x            = START_X;
        y            = START_Y;
        hp           = MAX_HP;
        lives        = MAX_LIVES;
        alive        = true;
        shootTimer   = 0;
        shieldActive = false;
        shieldTimer  = 0;
        weapon       = new WeaponModel();
    }

    public int         getLives()       { return lives; }
    public boolean     isShieldActive() { return shieldActive; }
    public WeaponModel getWeapon()      { return weapon; }
    public int         getShootTimer()  { return shootTimer; }
}
