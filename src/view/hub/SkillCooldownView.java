package view.hub;

import controller.skill.BossSkillController;
import java.awt.*;

public class SkillCooldownView {

    private final BossSkillController bossSkillController;

    public SkillCooldownView(BossSkillController bossSkillController) {
        this.bossSkillController = bossSkillController;
    }

    public void draw(Graphics2D g) {
        if (bossSkillController == null || !bossSkillController.getBoss().isAlive()) return;

        int x = 650;
        int y = 20;
        int barW = 120;
        int barH = 15;

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("Boss Skill CD:", x - 80, y + barH);

        // Vẽ thanh cooldown
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, barW, barH);

        g.setColor(Color.ORANGE);
        int fillW = (int) (barW * bossSkillController.getCooldownProgress());
        g.fillRect(x, y, fillW, barH);

        g.setColor(Color.WHITE);
        g.drawRect(x, y, barW, barH);
    }
}
