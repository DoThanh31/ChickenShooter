package view.entity.chicken;

import model.entity.chicken.BossChickenModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BossChickenView extends ChickenView {

    private final BufferedImage shieldImg;

    public BossChickenView(BossChickenModel model) {
        super(model, "assets/images/boss.png");
        this.shieldImg = SpriteLoader.getInstance().load("assets/images/shield.tj.png");
    }

    @Override
    public void draw(Graphics2D g) {
        if (!model.isAlive()) return;
        super.draw(g);

        if (((BossChickenModel) model).isShieldActive()) {
            if (shieldImg != null) {
                g.drawImage(shieldImg, (int) model.getX() - 10, (int) model.getY() - 10,
                        model.getW() + 20, model.getH() + 20, null);
            } else {
                g.setColor(new Color(255, 0, 0, 80));
                g.fillOval((int) model.getX() - 10, (int) model.getY() - 10,
                        model.getW() + 20, model.getH() + 20);
            }
        }

        drawBossHealth(g);
    }

    private void drawBossHealth(Graphics2D g) {
        int x = (int) model.getX();
        int y = (int) model.getY() - 15;
        int w = model.getW();
        int h = 10;

        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, w, h);

        g.setColor(Color.RED);
        int hpW = (int) (w * model.getHpRatio());
        g.fillRect(x, y, hpW, h);

        g.setColor(Color.WHITE);
        g.drawRect(x, y, w, h);
    }
}
