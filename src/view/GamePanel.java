package view;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GamePanel extends JPanel {

    public static final int HEIGHT = 600;
    public static final int WIDTH = 800;

    private BufferedImage backgroundImg;

    // 🐔 thêm chicken view
    private ChickenView chicken;

    public GamePanel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);

        try {
            backgroundImg = ImageIO.read(
                    new File("assets/images/Background_Menu.png")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // tạo gà ở vị trí (300, 200)
        chicken = new ChickenView(300, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // 🎨 background
        if (backgroundImg != null) {
            g2d.drawImage(backgroundImg, 0, 0, WIDTH, HEIGHT, null);
        }

        // 🐔 vẽ gà
        chicken.draw(g2d);
    }
}
