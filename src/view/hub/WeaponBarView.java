package view.hub;

import model.entity.PlayerModel;
import java.awt.*;

public class WeaponBarView {

    private final PlayerModel player;

    public WeaponBarView(PlayerModel player) {
        this.player = player;
    }

    public void draw(Graphics2D g) {
        int x = 20;
        int y = 60;
        int level = player.getWeapon().getLevel();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Weapon Level: " + level, x, y);

        // Vẽ thanh cấp độ (Progress bar)
        int barW = 100;
        int barH = 10;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y + 5, barW, barH);

        g.setColor(Color.CYAN);
        int fillW = (int) (barW * (level / 5.0f));
        g.fillRect(x, y + 5, fillW, barH);
        
        g.setColor(Color.WHITE);
        g.drawRect(x, y + 5, barW, barH);
    }
}
