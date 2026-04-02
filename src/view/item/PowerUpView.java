package view.item;

import model.item.PowerUpModel;
import util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUpView {

    private final PowerUpModel model;
    private final BufferedImage image;

    public PowerUpView(PowerUpModel model) {
        this.model = model;
        String path = switch (model.getPowerUpType()) {
            case HEAL -> "assets/images/item_hp.png";
            case SHIELD -> "assets/images/item_shield.png";
            case DAMAGE_UP -> "assets/images/item_weapon.png";
            case SCORE_DRUMSTICK -> "assets/images/item_chicken_leg.png";
        };
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
            // Nếu thiếu ảnh, vẽ màu vàng cho đùi gà
            g.setColor(model.getPowerUpType() == PowerUpModel.PowerUpType.SCORE_DRUMSTICK ? Color.YELLOW : Color.GREEN);
            g.fillRect(x, y, w, h);
        }
    }
}
