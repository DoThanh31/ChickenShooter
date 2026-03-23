package model.entity;

import model.weapon.WeaponModel;
import model.weapon.WeaponType;

/**
 * PlayerModel - Dữ liệu người chơi
 * extends EntityModel (có x, y, w, h, hp, alive)
 */
public class PlayerModel extends EntityModel {

    public static final int START_X    = 275;
    public static final int START_Y    = 620;
    public static final int WIDTH      = 50;
    public static final int HEIGHT     = 30;
    public static final int MAX_HP     = 3;   // máu mỗi tim
    public static final int MAX_LIVES  = 3;   // số tim
    public static final int SPEED      = 5;

    private int lives;           // số tim còn lại
    private int shootTimer;      // đếm cooldown bắn
    private boolean shieldActive;// đang có khiên không
    private int shieldTimer;     // thời gian khiên còn lại (frames)

    private WeaponModel weapon;  // vũ khí hiện tại

    public PlayerModel() {
        super(START_X, START_Y, WIDTH, HEIGHT, MAX_HP);
        this.lives        = MAX_LIVES;
        this.shootTimer   = 0;
        this.shieldActive = false;
        this.shieldTimer  = 0;
        this.weapon       = new WeaponModel();
    }

    // ── Logic ─────────────────────────────────────────────────

    /** Mất 1 tim, reset máu về MAX_HP nếu còn tim */
    public void loseLife() {
        lives--;
        if (lives > 0) {
            hp    = MAX_HP;
            alive = true;
        } else {
            alive = false; // hết tim → game over
        }
    }

    /** Nhận damage, nếu shield thì bỏ qua */
    @Override
    public void takeDamage(int damage) {
        if (shieldActive) {
            shieldActive = false;
            shieldTimer  = 0;
            return;
        }
        super.takeDamage(damage);
        if (!alive && lives > 0) loseLife(); // còn tim thì hồi
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
    public boolean     isGameOver()     { return lives <= 0; }
}