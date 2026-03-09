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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, WIDTH, HEIGHT, null);
        }
    }
}
