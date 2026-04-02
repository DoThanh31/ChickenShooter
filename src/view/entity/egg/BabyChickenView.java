package view.entity.egg;

import model.entity.egg.BabyChickenModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BabyChickenView {

    private final BabyChickenModel model;
    private final BufferedImage image;

    public BabyChickenView(BabyChickenModel model) {
        this.model = model;
        this.image = SpriteLoader.getInstance().load("assets/images/eggbaby.png");
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
            g.setColor(new Color(255, 230, 0));
            g.fillOval(x, y, w, h);
            
            g.setColor(Color.BLACK);
            g.fillOval(x + w - 8, y + 5, 3, 3);
            
            g.setColor(Color.ORANGE);
            int[] px = {x + w - 3, x + w + 2, x + w - 3};
            int[] py = {y + 7, y + 10, y + 13};
            g.fillPolygon(px, py, 3);
        }
        
        g.setColor(new Color(255, 255, 255, 50));
        g.drawOval(x - 2, y - 2, w + 4, h + 4);
    }
}
