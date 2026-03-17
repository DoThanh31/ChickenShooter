package manager;

import model.entity.*;
import model.entity.bullet.*;

import java.util.*;

public class GameModel {

    public enum State { MENU, PLAYING, BOSS_FIGHT, PAUSED, GAME_OVER, WIN }

    public static final int W = 480;
    public static final int H = 640;

    
    public final PlayerModel            player;
    public final List<ChickenModel>     chickens  = new ArrayList<>();
    public final BossModel[]            bossHolder = new BossModel[1];  
    public final List<BulletModel>      bullets   = new ArrayList<>();
    public final List<PowerUpModel>     powerUps  = new ArrayList<>();

    private State state = State.MENU;
    private int   wave  = 1;
    private int   spawnDelay = 0;

    public boolean inputLeft, inputRight, inputUp, inputDown, inputShoot;
    private int fireTimer = 0;
    private static final int FIRE_RATE = 12;  
    public GameModel() {
        player = new PlayerModel(W / 2f - 24, H - 100);
    }

    public void update() {
        if (state != State.PLAYING && state != State.BOSS_FIGHT) return;

        movePlayer();
        player.tickPowerUp();
        handlePlayerShooting();
        updateBullets();
        updateChickens();
        updateBoss();
        updatePowerUps();
        checkCollisions();
        checkWaveComplete();
    }

    private void movePlayer() {
        if (inputLeft)  player.moveLeft();
        if (inputRight) player.moveRight(W);
        if (inputUp)    player.moveUp();
        if (inputDown)  player.moveDown(H);
    }

    private void handlePlayerShooting() {
        if (!inputShoot) { fireTimer = 0; return; }
        fireTimer++;
        if (fireTimer % FIRE_RATE != 0 && fireTimer != 1) return;

        float cx = player.getCenterX(), cy = player.getY();

        if (player.isTripleShot()) {
            for (BulletModel b : BulletFactory.tripleShot(cx, cy)) bullets.add(b);
        } else if (player.isPierceShot()) {
            bullets.add(BulletFactory.pierceBullet(cx, cy));
        } else {
            bullets.add(BulletFactory.playerBullet(cx, cy));
        }
    }

    private void updateBullets() {
        bullets.removeIf(b -> {
            b.updatePos();
            return !b.isAlive() || b.isOutOfBounds(W, H);
        });
    }
 
    private void updateChickens() {
        for (ChickenModel c : chickens) {
            if (!c.isAlive()) continue;
            c.updatePos(0);
            if (c.tickShoot()) {
                bullets.add(BulletFactory.chickenBullet(c.getCenterX(), c.getCenterY()));
            }
        }
        chickens.removeIf(c -> !c.isAlive());
    }

    private void updateBoss() {
        BossModel boss = bossHolder[0];
        if (boss == null || !boss.isAlive()) return;

        boss.updatePos(W);

        if (boss.tickAimedShot()) {
            bullets.add(BulletFactory.bossBullet(
                    boss.getCenterX(), boss.getCenterY(),
                    player.getCenterX(), player.getCenterY()));
        }

        if (boss.getPhase() != BossModel.Phase.PHASE1 && boss.tickSpreadShot()) {
            if (boss.getPhase() == BossModel.Phase.PHASE3) {
                for (BulletModel b : BulletFactory.bossSpread8(boss.getCenterX(), boss.getCenterY()))
                    bullets.add(b);
            } else {
                for (BulletModel b : BulletFactory.bossFan5(
                        boss.getCenterX(), boss.getCenterY(),
                        player.getCenterX(), player.getCenterY()))
                    bullets.add(b);
            }
        }

        if (!boss.isAlive()) {
            state = State.WIN;
        }
    }

  
    private void updatePowerUps() {
        powerUps.removeIf(p -> {
            p.updatePos();
            return !p.isAlive() || p.getY() > H;
        });
    }

   
    private void checkCollisions() {
        Iterator<BulletModel> it = bullets.iterator();
        while (it.hasNext()) {
            BulletModel b = it.next();
            if (!b.isAlive()) { it.remove(); continue; }

            // Player bullets → enemies
            if (b.getOwner() == BulletModel.Owner.PLAYER) {
                boolean hit = false;

                // vs chickens
                for (ChickenModel c : chickens) {
                    if (c.isAlive() && b.collidesWith(c)) {
                        c.takeDamage(b.getDamage());
                        if (!c.isAlive()) {
                            player.addScore(c.getScoreValue());
                            maybeDropPowerUp(c.getCenterX(), c.getCenterY());
                        }
                        hit = true;
                        if (!b.isPierce()) break;
                    }
                }

                // vs boss
                BossModel boss = bossHolder[0];
                if (boss != null && boss.isAlive() && b.collidesWith(boss)) {
                    boss.takeDamage(b.getDamage());
                    player.addScore(5);
                    hit = true;
                }

                if (hit && !b.isPierce()) { b.reset(); it.remove(); }

            // Enemy bullets → player
            } else {
                if (b.collidesWith(player)) {
                    player.takeDamage(b.getDamage());
                    b.reset(); it.remove();
                    if (!player.isAlive()) { state = State.GAME_OVER; }
                }
            }
        }

        // Power-ups → player
        for (PowerUpModel p : powerUps) {
            if (p.isAlive() && p.collidesWith(player)) {
                applyPowerUp(p);
                p.reset();
            }
        }
    }

    private void maybeDropPowerUp(float x, float y) {
        if (Math.random() < 0.12) {
            PowerUpModel.Kind[] kinds = PowerUpModel.Kind.values();
            powerUps.add(new PowerUpModel(x - 14, y,
                    kinds[(int)(Math.random() * kinds.length)]));
        }
    }

    private void applyPowerUp(PowerUpModel p) {
        switch (p.getKind()) {
            case TRIPLE_SHOT -> player.activateTripleShot(360);
            case PIERCE_SHOT -> player.activatePierceShot(360);
            case HEAL        -> {
                int newHp = Math.min(PlayerModel.MAX_HP, player.getHp() + 30);
                // Reflection-free: expose a heal method
                player.heal(30);
            }
        }
    }

    // ── Wave management ──────────────────────────────────────────────────────
    private void checkWaveComplete() {
        if (chickens.isEmpty() && bossHolder[0] == null && state == State.PLAYING) {
            if (spawnDelay++ > 60) {
                spawnDelay = 0;
                wave++;
                if (wave % 5 == 0) spawnBoss();
                else               spawnWave(wave);
            }
        }
    }

    public void spawnWave(int waveNum) {
        chickens.clear();
        int cols = 8, rows = Math.min(2 + waveNum / 2, 5);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                ChickenModel.Type t = waveNum > 6  ? ChickenModel.Type.TANK
                                    : waveNum > 3  ? ChickenModel.Type.FAST
                                    :               ChickenModel.Type.NORMAL;
                chickens.add(new ChickenModel(
                        40 + c * 50f, 60 + r * 60f, t, c * 0.5f));
            }
        }
        state = State.PLAYING;
    }

    public void spawnBoss() {
        bossHolder[0] = new BossModel(W / 2f - 50, 80);
        state = State.BOSS_FIGHT;
    }

    // ── Public API ────────────────────────────────────────────────────────────
    public void startGame() {
        bullets.clear();
        chickens.clear();
        powerUps.clear();
        bossHolder[0] = null;
        player.reset();
        wave = 1;
        spawnWave(wave);
        state = State.PLAYING;
    }

    public void togglePause() {
        if (state == State.PLAYING || state == State.BOSS_FIGHT)
            state = State.PAUSED;
        else if (state == State.PAUSED)
            state = (bossHolder[0] != null ? State.BOSS_FIGHT : State.PLAYING);
    }

    public State getState() { return state; }
    public int   getWave()  { return wave; }
    public BossModel getBoss() { return bossHolder[0]; }
}
