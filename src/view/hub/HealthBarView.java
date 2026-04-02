package view.hub;

import model.entity.PlayerModel;
import java.awt.*;

public class HealthBarView {

    private final PlayerModel player;

    public HealthBarView(PlayerModel player) {
        this.player = player;
    }

    public void draw(Graphics2D g) {
        int lives = player.getLives();
        int hp = player.getHp();
        int maxHp = player.getMaxHp();

        int startX = 20;
        int startY = 20;
        int heartGap = 35;
        int heartSize = 24;

        for (int i = 0; i < lives; i++) {
            drawHeart(g, startX + (i * heartGap), startY, heartSize);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("HP: " + hp + "/" + maxHp, startX, startY + heartSize + 15);
    }

    private void drawHeart(Graphics2D g, int x, int y, int size) {
        g.setColor(new Color(255, 50, 50)); // MÃ u Ä‘á» tÆ°Æ¡i
        
        int circleSize = size / 2 + 2;
        g.fillOval(x, y, circleSize, circleSize);
        g.fillOval(x + size / 2 - 2, y, circleSize, circleSize);
        
        int[] tx = {x, x + size / 2, x + size};
        int[] ty = {y + circleSize / 2 + 2, y + size + 2, y + circleSize / 2 + 2};
        g.fillPolygon(tx, ty, 3);
        
        g.setColor(new Color(150, 0, 0));
        g.drawOval(x, y, circleSize, circleSize);
        g.drawOval(x + size / 2 - 2, y, circleSize, circleSize);
    }
}
