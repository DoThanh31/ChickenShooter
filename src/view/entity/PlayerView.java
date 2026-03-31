package view.entity;

import model.entity.PlayerModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerView {

    private final PlayerModel model;
    private final BufferedImage image;
    private final BufferedImage shieldImage;

    public PlayerView(PlayerModel model) {
        this.model = model;
        this.image = SpriteLoader.getInstance().load("assets/images/plane.png");
        this.shieldImage = SpriteLoader.getInstance().load("assets/images/shield_effect.png");
    }

    public void draw(Graphics2D g) {
        if (!model.isAlive()) return;

        int x = (int) model.getX();
        int y = (int) model.getY();
        int w = model.getW();
        int h = model.getH();

        if (image != null) {
            g.drawImage(image, x, y, w, h, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, w, h);
        }

        // Vẽ khiên nếu đang kích hoạt
        if (model.isShieldActive()) {
            if (shieldImage != null) {
                g.drawImage(shieldImage, x - 5, y - 5, w + 10, h + 10, null);
            } else {
                g.setColor(new Color(0, 255, 255, 100));
                g.fillOval(x - 5, y - 5, w + 10, h + 10);
            }
        }
    }
}
