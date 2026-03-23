package view;

import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChickenView {

    private float x, y;
    private int width = 64;
    private int height = 64;

    private BufferedImage image;

    public ChickenView(float x, float y) {
        this.x = x;
        this.y = y;

        image = SpriteLoader.getInstance()
                .load("assets/images/bird1.png");
    }

    public void draw(Graphics2D g) {
        if (image != null) {
            g.drawImage(image, (int)x, (int)y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect((int)x, (int)y, width, height);
        }
    }
}
