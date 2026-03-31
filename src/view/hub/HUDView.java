package view.hub;

import controller.GameController;
import controller.skill.BossSkillController;
import model.GameModel;
import model.entity.PlayerModel;

import java.awt.*;

public class HUDView {

    private final GameModel gameModel;
    private final PlayerModel player;
    private final HealthBarView healthBar;
    private final WeaponBarView weaponBar;
    private final SkillCooldownView skillCooldown;

    public HUDView(GameController gameController) {
        this.gameModel = gameController.getGameModel();
        this.player = gameController.getPlayer();
        this.healthBar = new HealthBarView(player);
        this.weaponBar = new WeaponBarView(player);
        
        // Cần truyền BossSkillController nếu có
        // Giả sử có cách để lấy từ GameController
        this.skillCooldown = null; 
    }

    public void draw(Graphics2D g) {
        // Vẽ điểm số
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + gameModel.getScore(), 20, 110);
        g.drawString("Level: " + gameModel.getLevel(), 20, 135);

        // Vẽ HP và Vũ khí
        healthBar.draw(g);
        weaponBar.draw(g);

        if (skillCooldown != null) {
            skillCooldown.draw(g);
        }

        // Nếu Game Over
        if (gameModel.isGameOver()) {
            drawGameOver(g);
        }
    }

    private void drawGameOver(Graphics2D g) {
        String msg = gameModel.getPhase() == GameModel.Phase.WIN ? "YOU WIN!" : "GAME OVER";
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 800, 600);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics fm = g.getFontMetrics();
        int x = (800 - fm.stringWidth(msg)) / 2;
        int y = (600 / 2);
        g.drawString(msg, x, y);
    }
}
