package view.hub;

import controller.GameController;
import model.GameModel;
import model.entity.PlayerModel;

import java.awt.*;

public class HUDView {

    private final GameModel gameModel;
    private final PlayerModel player;
    private final HealthBarView healthBar;
    private final WeaponBarView weaponBar;

    public HUDView(GameController gameController) {
        this.gameModel = gameController.getGameModel();
        this.player = gameController.getPlayer();
        this.healthBar = new HealthBarView(player);
        this.weaponBar = new WeaponBarView(player);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + gameModel.getScore(), 20, 110);
        g.setColor(Color.YELLOW);
        g.drawString("High Score: " + gameModel.getHighScore(), 20, 135);
        g.setColor(Color.WHITE);
        g.drawString("Level: " + gameModel.getLevel(), 20, 160);

        healthBar.draw(g);
        weaponBar.draw(g);

        if (gameModel.isLevelUp()) {
            drawLevelUp(g);
        }

        if (gameModel.isGameOver()) {
            drawGameOver(g);
        }
    }

    private void drawLevelUp(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String msg = "LEVEL " + gameModel.getLevel();
        int x = (800 - g.getFontMetrics().stringWidth(msg)) / 2;
        g.drawString(msg, x, 300);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        String sub = "Get Ready...";
        int sx = (800 - g.getFontMetrics().stringWidth(sub)) / 2;
        g.drawString(sub, sx, 350);
    }

    private void drawGameOver(Graphics2D g) {
        String msg = gameModel.getPhase() == GameModel.Phase.WIN ? "YOU WIN!" : "GAME OVER";
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 800, 600);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics fm = g.getFontMetrics();
        int x = (800 - fm.stringWidth(msg)) / 2;
        int y = (600 / 2) - 30;
        g.drawString(msg, x, y);

        g.setFont(new Font("Arial", Font.BOLD, 25));
        String scoreMsg = "Final Score: " + gameModel.getScore();
        int sx = (800 - g.getFontMetrics().stringWidth(scoreMsg)) / 2;
        g.drawString(scoreMsg, sx, y + 60);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        String hint = "Press R to Restart or ESC for Menu";
        int hx = (800 - g.getFontMetrics().stringWidth(hint)) / 2;
        g.drawString(hint, hx, y + 100);
    }
}
