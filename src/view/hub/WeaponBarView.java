package view.hub;

import model.entity.PlayerModel;
import java.awt.*;

/**
 * WeaponBarView - Hiện tại đã được ẩn đi theo yêu cầu của người chơi
 */
public class WeaponBarView {

    private final PlayerModel player;

    public WeaponBarView(PlayerModel player) {
        this.player = player;
    }

    public void draw(Graphics2D g) {
        // Không vẽ gì cả ở mọi level
    }
}
