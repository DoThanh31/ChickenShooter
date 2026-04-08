package view.item;

import model.item.WeaponItemModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponItemView {

    private final WeaponItemModel model;
    private final BufferedImage image;

    public WeaponItemView(WeaponItemModel model) {
        this.model = model;
        this.image = SpriteLoader.getInstance().load("assets/images/weapon.png");
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
            g.setColor(Color.ORANGE);
            g.fillRect(x, y, w, h);
        }
    }
}
