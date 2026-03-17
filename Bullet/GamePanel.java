package view;

import model.entity.*;
import model.entity.bullet.*;
import model.manager.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;

/**
 * Renders the entire game using Java2D.
 * Also handles keyboard input and drives the game loop via a Swing Timer.
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {

    // ── Layout ───────────────────────────────────────────────────────────────
    private static final int PANEL_W = GameModel.W;
    private static final int PANEL_H = GameModel.H;
    private static final int FPS     = 60;

    // ── Colors / palette ─────────────────────────────────────────────────────
    private static final Color BG_TOP    = new Color(5,  10,  30);
    private static final Color BG_BOT    = new Color(15, 25, 70);
    private static final Color PLAYER_C  = new Color(80, 200, 255);
    private static final Color BULLET_PL = new Color(255, 255, 100);
    private static final Color BULLET_EN = new Color(255,  80,  60);
    private static final Color BULLET_BO = new Color(255, 140,  30);
    private static final Color CHICKEN_N = new Color(255, 200,  60);
    private static final Color CHICKEN_F = new Color(255, 130,  30);
    private static final Color CHICKEN_T = new Color(190,  90, 220);
    private static final Color BOSS_C    = new Color(200,  40,  40);
    private static final Color HP_GREEN  = new Color(60,  220,  60);
    private static final Color HP_RED    = new Color(220,  50,  50);
    private static final Color STAR_C    = new Color(255, 255, 255, 120);

    // ── Stars (parallax background) ──────────────────────────────────────────
    private static final int STAR_COUNT = 80;
    private final float[] starX = new float[STAR_COUNT];
    private final float[] starY = new float[STAR_COUNT];
    private final float[] starS = new float[STAR_COUNT]; // size
    private final float[] starV = new float[STAR_COUNT]; // speed

    // ── Game ─────────────────────────────────────────────────────────────────
    private final GameModel game;
    private final javax.swing.Timer gameTimer;

    // ── Input ─────────────────────────────────────────────────────────────────
    private boolean kLeft, kRight, kUp, kDown, kShoot, kPause;

    // ── Particle flash ────────────────────────────────────────────────────────
    private int   flashTimer = 0;
    private float flashX, flashY;

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        game = new GameModel();

        initStars();

        gameTimer = new javax.swing.Timer(1000 / FPS, this);
        gameTimer.start();
    }

    // ── Initialization ────────────────────────────────────────────────────────
    private void initStars() {
        for (int i = 0; i < STAR_COUNT; i++) {
            starX[i] = (float)(Math.random() * PANEL_W);
            starY[i] = (float)(Math.random() * PANEL_H);
            starS[i] = (float)(Math.random() * 2.5 + 0.5);
            starV[i] = (float)(Math.random() * 1.2 + 0.3);
        }
    }

    // ── Game-loop tick ────────────────────────────────────────────────────────
    @Override
    public void actionPerformed(ActionEvent e) {
        // Push input to model
        game.inputLeft  = kLeft;
        game.inputRight = kRight;
        game.inputUp    = kUp;
        game.inputDown  = kDown;
        game.inputShoot = kShoot;

        // Pause toggle
        if (kPause) { game.togglePause(); kPause = false; }

        // Update model
        game.update();

        // Scroll stars
        for (int i = 0; i < STAR_COUNT; i++) {
            starY[i] += starV[i];
            if (starY[i] > PANEL_H) { starY[i] = 0; starX[i] = (float)(Math.random() * PANEL_W); }
        }

        // Flash decay
        if (flashTimer > 0) flashTimer--;

        repaint();
    }

    // ── Rendering ─────────────────────────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2);
        drawStars(g2);

        GameModel.State state = game.getState();

        switch (state) {
            case MENU      -> drawMenu(g2);
            case GAME_OVER -> drawGameOver(g2);
            case WIN       -> drawWin(g2);
            case PAUSED    -> { drawGame(g2); drawPause(g2); }
            default        -> drawGame(g2);
        }
    }

    private void drawBackground(Graphics2D g2) {
        GradientPaint gp = new GradientPaint(0, 0, BG_TOP, 0, PANEL_H, BG_BOT);
        g2.setPaint(gp);
        g2.fillRect(0, 0, PANEL_W, PANEL_H);
    }

    private void drawStars(Graphics2D g2) {
        g2.setColor(STAR_C);
        for (int i = 0; i < STAR_COUNT; i++) {
            float s = starS[i];
            g2.fill(new Ellipse2D.Float(starX[i], starY[i], s, s));
        }
    }

    private void drawGame(Graphics2D g2) {
        drawChickens(g2);
        drawBoss(g2);
        drawBullets(g2);
        drawPowerUps(g2);
        drawPlayer(g2);
        drawHUD(g2);
        if (flashTimer > 0) drawFlash(g2);
    }

    // ── Draw player ───────────────────────────────────────────────────────────
    private void drawPlayer(Graphics2D g2) {
        PlayerModel p = game.player;
        if (!p.isAlive()) return;

        float x = p.getX(), y = p.getY(), w = p.getW(), h = p.getH();

        // Body - ship shape
        g2.setColor(PLAYER_C);
        int[] xs = { (int)(x + w/2), (int)(x + w), (int)(x + w - 8), (int)(x + 8), (int)(x) };
        int[] ys = { (int)y, (int)(y + h), (int)(y + h - 10), (int)(y + h - 10), (int)(y + h) };
        g2.fillPolygon(xs, ys, xs.length);

        // Cockpit
        g2.setColor(new Color(30, 80, 180));
        g2.fill(new Ellipse2D.Float(x + w/2f - 8, y + 8, 16, 18));

        // Engine glow
        g2.setColor(new Color(100, 200, 255, 180));
        g2.fill(new Ellipse2D.Float(x + w/2f - 5, y + h - 5, 10, 12));

        // Power-up aura
        if (p.isTripleShot() || p.isPierceShot()) {
            Color aura = p.isTripleShot()
                    ? new Color(255, 220, 0, 60)
                    : new Color(150, 80, 255, 60);
            g2.setColor(aura);
            g2.fill(new RoundRectangle2D.Float(x - 4, y - 4, w + 8, h + 8, 16, 16));
        }
    }

    // ── Draw chickens ─────────────────────────────────────────────────────────
    private void drawChickens(Graphics2D g2) {
        for (ChickenModel c : game.chickens) {
            if (!c.isAlive()) continue;
            Color col = switch (c.getType()) {
                case NORMAL -> CHICKEN_N;
                case FAST   -> CHICKEN_F;
                case TANK   -> CHICKEN_T;
            };
            float x = c.getX(), y = c.getY(), cw = c.getW(), ch = c.getH();
            // Body
            g2.setColor(col);
            g2.fill(new Ellipse2D.Float(x + cw * 0.1f, y + ch * 0.3f, cw * 0.8f, ch * 0.6f));
            // Head
            g2.fill(new Ellipse2D.Float(x + cw * 0.25f, y, cw * 0.5f, ch * 0.45f));
            // Eye
            g2.setColor(Color.RED);
            g2.fill(new Ellipse2D.Float(x + cw * 0.55f, y + ch * 0.08f, cw * 0.12f, ch * 0.12f));
            // Beak
            g2.setColor(new Color(255, 160, 0));
            int[] bx = { (int)(x + cw * 0.74f), (int)(x + cw), (int)(x + cw * 0.74f) };
            int[] by = { (int)(y + ch * 0.22f), (int)(y + ch * 0.30f), (int)(y + ch * 0.38f) };
            g2.fillPolygon(bx, by, 3);
            // HP bar (TANK only)
            if (c.getType() == ChickenModel.Type.TANK) drawHpBar(g2, x, y - 8, cw, c.getHp(), 3);
        }
    }

    // ── Draw boss ─────────────────────────────────────────────────────────────
    private void drawBoss(Graphics2D g2) {
        BossModel boss = game.getBoss();
        if (boss == null || !boss.isAlive()) return;

        float x = boss.getX(), y = boss.getY(), bw = boss.getW(), bh = boss.getH();

        // Outer glow
        Color glow = switch (boss.getPhase()) {
            case PHASE1 -> new Color(200, 40, 40, 60);
            case PHASE2 -> new Color(255, 140, 0, 80);
            case PHASE3 -> new Color(255, 50, 255, 100);
        };
        g2.setColor(glow);
        g2.fill(new Ellipse2D.Float(x - 10, y - 10, bw + 20, bh + 20));

        // Body
        g2.setColor(BOSS_C);
        g2.fill(new RoundRectangle2D.Float(x, y, bw, bh, 20, 20));

        // Eyes
        g2.setColor(Color.YELLOW);
        g2.fill(new Ellipse2D.Float(x + 18, y + 25, 18, 18));
        g2.fill(new Ellipse2D.Float(x + bw - 36, y + 25, 18, 18));
        g2.setColor(Color.BLACK);
        g2.fill(new Ellipse2D.Float(x + 22, y + 29, 10, 10));
        g2.fill(new Ellipse2D.Float(x + bw - 32, y + 29, 10, 10));

        // HP bar
        drawHpBar(g2, x, y - 14, bw, boss.getHp(), 300);

        // Phase label
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 10));
        g2.drawString("PHASE " + (boss.getPhase().ordinal() + 1),
                (int)(x + bw / 2f - 22), (int)(y + bh + 14));
    }

    // ── Draw bullets ─────────────────────────────────────────────────────────
    private void drawBullets(Graphics2D g2) {
        for (BulletModel b : game.bullets) {
            Color c = switch (b.getOwner()) {
                case PLAYER  -> b.isPierce() ? new Color(180, 100, 255) : BULLET_PL;
                case CHICKEN -> BULLET_EN;
                case BOSS    -> BULLET_BO;
            };
            g2.setColor(c);

            // Glow
            g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 60));
            g2.fill(new Ellipse2D.Float(b.getX() - 3, b.getY() - 3, b.getW() + 6, b.getH() + 6));

            g2.setColor(c);
            if (b.getOwner() == BulletModel.Owner.PLAYER) {
                // Elongated capsule
                g2.fill(new RoundRectangle2D.Float(b.getX(), b.getY(), b.getW(), b.getH(), 6, 6));
            } else {
                g2.fill(new Ellipse2D.Float(b.getX(), b.getY(), b.getW(), b.getH()));
            }
        }
    }

    // ── Draw power-ups ────────────────────────────────────────────────────────
    private void drawPowerUps(Graphics2D g2) {
        for (PowerUpModel p : game.powerUps) {
            if (!p.isAlive()) continue;
            Color c = switch (p.getKind()) {
                case TRIPLE_SHOT -> new Color(255, 220, 0);
                case PIERCE_SHOT -> new Color(180, 100, 255);
                case HEAL        -> new Color(80, 255, 120);
            };
            g2.setColor(c);
            g2.fill(new RoundRectangle2D.Float(p.getX(), p.getY(), p.getW(), p.getH(), 8, 8));
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Monospaced", Font.BOLD, 10));
            String lbl = switch (p.getKind()) {
                case TRIPLE_SHOT -> "3x";
                case PIERCE_SHOT -> "Px";
                case HEAL        -> "+H";
            };
            g2.drawString(lbl, (int)p.getX() + 4, (int)p.getY() + 18);
        }
    }

    // ── HUD ───────────────────────────────────────────────────────────────────
    private void drawHUD(Graphics2D g2) {
        PlayerModel p = game.player;
        g2.setFont(new Font("Monospaced", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString("SCORE: " + p.getScore(), 8, 20);
        g2.drawString("WAVE:  " + game.getWave(),  8, 38);
        g2.drawString("LIVES: " + p.getLives(),    8, 56);

        // HP bar
        drawHpBar(g2, PANEL_W - 140, 8, 130, p.getHp(), PlayerModel.MAX_HP);

        // Power-up indicator
        if (p.isTripleShot() || p.isPierceShot()) {
            String label = p.isTripleShot() ? "TRIPLE" : "PIERCE";
            Color  col   = p.isTripleShot() ? new Color(255,220,0) : new Color(180,100,255);
            g2.setColor(col);
            g2.setFont(new Font("Monospaced", Font.BOLD, 12));
            g2.drawString(label + " [" + p.getPowerTimer() / 60 + "s]", PANEL_W - 140, 36);
        }

        // Boss fight label
        if (game.getState() == GameModel.State.BOSS_FIGHT) {
            g2.setColor(new Color(255, 60, 60));
            g2.setFont(new Font("Monospaced", Font.BOLD, 18));
            String txt = "  BOSS FIGHT!";
            g2.drawString(txt, PANEL_W / 2 - 70, PANEL_H - 20);
        }
    }

    private void drawHpBar(Graphics2D g2, float x, float y, float maxW, int hp, int maxHp) {
        float ratio = Math.max(0, (float) hp / maxHp);
        g2.setColor(new Color(40, 40, 40));
        g2.fill(new RoundRectangle2D.Float(x, y, maxW, 8, 4, 4));
        Color c = ratio > 0.5f ? HP_GREEN : ratio > 0.25f ? new Color(240,200,0) : HP_RED;
        g2.setColor(c);
        g2.fill(new RoundRectangle2D.Float(x, y, maxW * ratio, 8, 4, 4));
        g2.setColor(Color.WHITE);
        g2.draw(new RoundRectangle2D.Float(x, y, maxW, 8, 4, 4));
    }

    private void drawFlash(Graphics2D g2) {
        int a = (int)(flashTimer / 5.0 * 80);
        g2.setColor(new Color(255, 255, 200, a));
        g2.fill(new Ellipse2D.Float(flashX - 20, flashY - 20, 40, 40));
    }

    // ── Overlay screens ───────────────────────────────────────────────────────
    private void drawMenu(Graphics2D g2) {
        drawOverlay(g2, "CHICKEN SHOOTER",
                "Arrow / WASD  →  Move",
                "SPACE          →  Fire",
                "P              →  Pause",
                "",
                "Press ENTER to Start");
    }

    private void drawPause(Graphics2D g2) {
        drawOverlay(g2, "PAUSED", "", "Press P to resume", "", "", "");
    }

    private void drawGameOver(Graphics2D g2) {
        drawOverlay(g2, "GAME  OVER",
                "Score: " + game.player.getScore(),
                "", "", "",
                "Press ENTER to restart");
    }

    private void drawWin(Graphics2D g2) {
        drawOverlay(g2, "YOU  WIN!",
                "Score: " + game.player.getScore(),
                "", "", "",
                "Press ENTER to restart");
    }

    private void drawOverlay(Graphics2D g2, String title, String... lines) {
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, PANEL_W, PANEL_H);

        g2.setFont(new Font("Monospaced", Font.BOLD, 30));
        FontMetrics fm = g2.getFontMetrics();
        int ty = PANEL_H / 2 - 60;

        // Title with glow
        g2.setColor(new Color(80, 200, 255, 80));
        g2.drawString(title, (PANEL_W - fm.stringWidth(title)) / 2 + 2, ty + 2);
        g2.setColor(new Color(80, 200, 255));
        g2.drawString(title, (PANEL_W - fm.stringWidth(title)) / 2, ty);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        fm = g2.getFontMetrics();
        int y = ty + 50;
        for (String line : lines) {
            g2.setColor(Color.WHITE);
            g2.drawString(line, (PANEL_W - fm.stringWidth(line)) / 2, y);
            y += 22;
        }
    }

    // ── KeyListener ───────────────────────────────────────────────────────────
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT,  KeyEvent.VK_A -> kLeft  = true;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> kRight = true;
            case KeyEvent.VK_UP,    KeyEvent.VK_W -> kUp    = true;
            case KeyEvent.VK_DOWN,  KeyEvent.VK_S -> kDown  = true;
            case KeyEvent.VK_SPACE               -> kShoot = true;
            case KeyEvent.VK_P                   -> kPause = true;
            case KeyEvent.VK_ENTER -> {
                GameModel.State s = game.getState();
                if (s == GameModel.State.MENU || s == GameModel.State.GAME_OVER
                        || s == GameModel.State.WIN) {
                    game.startGame();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT,  KeyEvent.VK_A -> kLeft  = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> kRight = false;
            case KeyEvent.VK_UP,    KeyEvent.VK_W -> kUp    = false;
            case KeyEvent.VK_DOWN,  KeyEvent.VK_S -> kDown  = false;
            case KeyEvent.VK_SPACE               -> kShoot = false;
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
}
