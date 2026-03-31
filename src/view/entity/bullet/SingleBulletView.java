package view.entity.bullet;

import model.entity.bullet.BulletModel;
import model.entity.bullet.SingleBulletModel;

import java.awt.*;

public class SingleBulletView extends BulletView {

    public SingleBulletView(SingleBulletModel model) {
        super(model, "assets/images/ammo.png");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
