package model.entity;

public class PlayerModel extends EntityModel {

    public static final int START_X    = 275;
    public static final int START_Y    = 620;
    public static final int WIDTH      = 50;
    public static final int HEIGHT     = 30;
    public static final int MAX_HP     = 3;
    public static final int MAX_LIVES  = 3;
    public static final int SPEED      = 5;

    private int lives;
    private int shootTimer;
    private boolean shieldActive;
    private int shieldTimer;


    public PlayerModel() {
        super(START_X, START_Y, WIDTH, HEIGHT, MAX_HP);
        this.lives        = MAX_LIVES;
        this.shootTimer   = 0;
        this.shieldActive = false;
        this.shieldTimer  = 0;

    }


    public void loseLife() {
        lives--;
        if (lives > 0) {
            hp    = MAX_HP;
            alive = true;
        } else {
            alive = false; // hết tim → game over
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

    }

    public int         getLives()       { return lives; }
    public boolean     isShieldActive() { return shieldActive; }
    public int         getShootTimer()  { return shootTimer; }
    public boolean     isGameOver()     { return lives <= 0; }
}
