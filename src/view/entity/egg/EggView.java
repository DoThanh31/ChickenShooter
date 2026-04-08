package view.entity.egg;

import model.entity.egg.EggModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EggView {

    private final EggModel model;
    private final BufferedImage[] crackImages; // Mảng chứa 3 giai đoạn nứt của trứng

    public EggView(EggModel model) {
        this.model = model;
        this.crackImages = new BufferedImage[3];

    }

    public void draw(Graphics2D g) {
        if (!model.isAlive()) return;

        int x = (int) model.getX();
        int y = (int) model.getY();
        int w = model.getW();
        int h = model.getH();

        int stage = model.getCrackStage();
        stage = Math.min(stage, crackImages.length - 1);

        if (crackImages[stage] != null) {
            g.drawImage(crackImages[stage], x, y, w, h, null);
        } else {
            g.setColor(new Color(255, 253, 208));
            g.fillOval(x, y, w, h);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, w, h);
            
            if (stage > 0) {
                g.setColor(Color.DARK_GRAY);
                g.drawLine(x + w/2, y + 5, x + w/2 - 3, y + 10);
                if (stage > 1) {
                    g.drawLine(x + w/2 - 3, y + 10, x + w/2 + 5, y + 15);
                }
            }
        }
    }
}
