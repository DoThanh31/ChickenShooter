package view;

import controller.GameController;
import model.entity.bullet.BulletModel;
import model.entity.bullet.ChickenBulletModel;
import model.entity.bullet.DoubleBulletModel;
import model.entity.bullet.SingleBulletModel;
import model.entity.chicken.BossChickenModel;
import model.entity.chicken.ChickenModel;
import model.entity.chicken.EggChickenModel;
import model.entity.chicken.NormalChickenModel;
import model.item.ItemModel;
import model.item.PowerUpModel;
import model.item.WeaponItemModel;
import view.entity.PlayerView;
import view.entity.bullet.BulletView;
import view.entity.bullet.ChickenBulletView;
import view.entity.bullet.DoubleBulletView;
import view.entity.bullet.SingleBulletView;
import view.entity.chicken.BossChickenView;
import view.entity.chicken.ChickenView;
import view.entity.chicken.EggChickenView;
import view.entity.chicken.NormalChickenView;
import view.hub.HUDView;
import view.item.PowerUpView;
import view.item.WeaponItemView;
import view.skill.LaserView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class GamePanel extends JPanel {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private BufferedImage backgroundImg;
    private int bgY1 = 0;
    private int bgY2 = -HEIGHT;
    private final int bgSpeed = 2; // Tốc độ trôi của nền

    // View components
    private final PlayerView playerView;
    private final HUDView hudView;
    private final GameController gameController;
    private final LaserView laserView;

    public GamePanel(GameController gameController) {
        this.gameController = gameController;
        this.playerView = new PlayerView(gameController.getPlayer());
        this.hudView = new HUDView(gameController);
        this.laserView = new LaserView(gameController.getSkillController().getLaserSkill());

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);

        try {
            backgroundImg = ImageIO.read(new File("assets/images/universe-bg.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Cập nhật vị trí background tạo hiệu ứng vô tận */
    public void updateBackground() {
        bgY1 += bgSpeed;
        bgY2 += bgSpeed;

        if (bgY1 >= HEIGHT) bgY1 = -HEIGHT;
        if (bgY2 >= HEIGHT) bgY2 = -HEIGHT;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Vẽ Background trôi (sử dụng 2 tấm ảnh nối đuôi nhau)
        if (backgroundImg != null) {
            g2d.drawImage(backgroundImg, 0, bgY1, WIDTH, HEIGHT, null);
            g2d.drawImage(backgroundImg, 0, bgY2, WIDTH, HEIGHT, null);
        }

        // Vẽ Player
        playerView.draw(g2d);

        // Vẽ Đạn
        List<BulletModel> bullets = gameController.getBullets();
        for (BulletModel b : bullets) {
            BulletView bv = getBulletView(b);
            if (bv != null) bv.draw(g2d);
        }

        // Vẽ Gà
        List<ChickenModel> chickens = gameController.getChickens();
        for (ChickenModel c : chickens) {
            ChickenView cv = getChickenView(c);
            if (cv != null) cv.draw(g2d);
        }

        // Vẽ Items
        List<ItemModel> items = gameController.getItems();
        for (ItemModel item : items) {
            drawItem(item, g2d);
        }

        // Vẽ Laser (Skill Boss)
        laserView.draw(g2d);

        // Vẽ HUD
        hudView.draw(g2d);
    }

    private ChickenView getChickenView(ChickenModel model) {
        if (model instanceof BossChickenModel) return new BossChickenView((BossChickenModel) model);
        if (model instanceof EggChickenModel) return new EggChickenView((EggChickenModel) model);
        if (model instanceof NormalChickenModel) return new NormalChickenView((NormalChickenModel) model);
        return null;
    }

    private BulletView getBulletView(BulletModel model) {
        if (model instanceof SingleBulletModel) return new SingleBulletView((SingleBulletModel) model);
        if (model instanceof DoubleBulletModel) return new DoubleBulletView((DoubleBulletModel) model);
        if (model instanceof ChickenBulletModel) return new ChickenBulletView((ChickenBulletModel) model);
        return null;
    }

    private void drawItem(ItemModel model, Graphics2D g2d) {
        if (model instanceof PowerUpModel) new PowerUpView((PowerUpModel) model).draw(g2d);
        else if (model instanceof WeaponItemModel) new WeaponItemView((WeaponItemModel) model).draw(g2d);
    }
}
