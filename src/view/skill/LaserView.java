package view.skill;

import model.skill.LaserSkillModel;
import java.awt.*;

public class LaserView {

    private final LaserSkillModel model;

    public LaserView(LaserSkillModel model) {
        this.model = model;
    }

    public void draw(Graphics2D g) {
        if (!model.isActive()) return;

        int y = (int) model.getTargetY();
        int height = 15; // Độ dày của tia laser

        // Hiệu ứng tia laser
        // Lớp ngoài (màu đỏ mờ)
        g.setColor(new Color(255, 0, 0, 100));
        g.fillRect(0, y - 5, 800, height + 10);

        // Lớp trong (màu đỏ đậm)
        g.setColor(new Color(255, 50, 50, 180));
        g.fillRect(0, y - 2, 800, height + 4);

        // Lõi tia laser (màu trắng)
        g.setColor(Color.WHITE);
        g.fillRect(0, y, 800, height);
        
        // Vẽ tia sét/điện nhỏ chạy dọc laser (nếu muốn cầu kỳ hơn)
        g.setColor(new Color(255, 255, 200, 150));
        for (int i = 0; i < 800; i += 40) {
            g.drawLine(i, y + 7, i + 20, y + 2);
        }
    }
}
