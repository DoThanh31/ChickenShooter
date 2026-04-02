package view.entity;

import model.entity.PlayerModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerView {

    private final PlayerModel model;
    private final BufferedImage image;

    public PlayerView(PlayerModel model) {
        this.model = model;
        this.image = SpriteLoader.getInstance().load("assets/images/plane.png");
    }

    public void draw(Graphics2D g) {
        if (!model.isAlive()) return;

        int x = (int) model.getX();
        int y = (int) model.getY();
        int w = model.getW();
        int h = model.getH();

        // 1. Vẽ Phi thuyền
        if (image != null) {
            g.drawImage(image, x, y, w, h, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, w, h);
        }

        // 2. Vẽ Khiên (Shield) bằng tay
        if (model.isShieldActive()) {
            drawShieldEffect(g, x, y, w, h);
        }
    }

    /** Vẽ hiệu ứng khiên bảo vệ thủ công bằng vòng tròn tỏa sáng */
    private void drawShieldEffect(Graphics2D g, int x, int y, int w, int h) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Tạo hiệu ứng vòng tròn xanh nhạt trong suốt bao quanh
        g2d.setColor(new Color(0, 255, 255, 120));
        g2d.setStroke(new BasicStroke(3)); // Độ dày của vòng khiên
        
        int offset = 10;
        g2d.drawOval(x - offset, y - offset, w + offset * 2, h + offset * 2);
        
        // Thêm một vòng mờ hơn bên ngoài
        g2d.setColor(new Color(0, 255, 255, 60));
        g2d.drawOval(x - offset - 4, y - offset - 4, w + (offset + 4) * 2, h + (offset + 4) * 2);
        
        g2d.dispose();
    }
}
