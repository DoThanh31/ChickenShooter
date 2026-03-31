package view.entity.bullet;

import model.entity.bullet.BulletModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BulletView {

    protected BulletModel model;
    protected BufferedImage image;

    public BulletView(BulletModel model, String path) {
        this.model = model;
        this.image = SpriteLoader.getInstance().load(path);
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
            g.setColor(model.getOwner() == BulletModel.Owner.PLAYER ? Color.YELLOW : Color.RED);
            g.fillRect(x, y, w, h);
        }
    }
}
