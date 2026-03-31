package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuPanel extends JPanel {

    private BufferedImage backgroundImg;
    private final JButton startButton;
    private final JButton exitButton;

    public MenuPanel(ActionListener startAction, ActionListener exitAction) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null); // Sử dụng null layout để tự do đặt vị trí button

        try {
            backgroundImg = ImageIO.read(new File("assets/images/Background_Menu.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- Cấu hình Button ---
        startButton = createButton("START GAME", 300, 300);
        exitButton = createButton("EXIT", 300, 380);

        startButton.addActionListener(startAction);
        exitButton.addActionListener(exitAction);

        add(startButton);
        add(exitButton);
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 200, 50);
        btn.setFocusable(false);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(new Color(255, 200, 0));
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Vẽ hình nền
        if (backgroundImg != null) {
            g2d.drawImage(backgroundImg, 0, 0, 800, 600, null);
        } else {
            g2d.setColor(new Color(20, 20, 60));
            g2d.fillRect(0, 0, 800, 600);
        }

        // Vẽ tiêu đề Game
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 60));
        FontMetrics fm = g2d.getFontMetrics();

    }
}
