package view.hub;

import model.entity.PlayerModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthBarView {

    private final PlayerModel player;
    private final BufferedImage heartImg;

    public HealthBarView(PlayerModel player) {
        this.player = player;
        this.heartImg = SpriteLoader.getInstance().load("assets/images/heart.png");
    }

    public void draw(Graphics2D g) {
        int lives = player.getLives();
        int x = 20;
        int y = 20;

        for (int i = 0; i < lives; i++) {
            if (heartImg != null) {
                g.drawImage(heartImg, x + (i * 30), y, 24, 24, null);
            } else {
                g.setColor(Color.RED);
                g.fillOval(x + (i * 30), y, 20, 20);
            }
        }
    }
}
