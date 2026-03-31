package view.entity.bullet;

import model.entity.bullet.ChickenBulletModel;

import java.awt.*;

public class ChickenBulletView extends BulletView {

    public ChickenBulletView(ChickenBulletModel model) {
        super(model, "assets/images/bullet_chicken.png");
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
