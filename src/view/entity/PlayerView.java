package view.entity;

import model.entity.PlayerModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerView {

    private final PlayerModel model;
    private final BufferedImage image;

    public PlayerView(PlayerModel model) {
        this.model = model;
        this.image = SpriteLoader.getInstance().load("assets/images/plane.png");
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

        if (model.isShieldActive()) {
            drawShieldEffect(g, x, y, w, h);
        }
    }

    private void drawShieldEffect(Graphics2D g, int x, int y, int w, int h) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setColor(new Color(0, 255, 255, 120));
        g2d.setStroke(new BasicStroke(3)); // Äá»™ dÃ y cá»§a vÃ²ng khiÃªn
        
        int offset = 10;
        g2d.drawOval(x - offset, y - offset, w + offset * 2, h + offset * 2);
        
        g2d.setColor(new Color(0, 255, 255, 60));
        g2d.drawOval(x - offset - 4, y - offset - 4, w + (offset + 4) * 2, h + (offset + 4) * 2);
        
        g2d.dispose();
    }
}
