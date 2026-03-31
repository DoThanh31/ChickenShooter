package view.entity.bullet;

import model.entity.bullet.DoubleBulletModel;

import java.awt.*;

public class DoubleBulletView extends BulletView {

    public DoubleBulletView(DoubleBulletModel model) {
        super(model, "assets/images/Bullet_1.png");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
