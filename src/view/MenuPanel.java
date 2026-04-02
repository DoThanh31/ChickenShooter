package view;

import util.ScoreManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class MenuPanel extends JPanel {

    private BufferedImage backgroundImg;
    private final JButton startButton;
    private final JButton settingsButton;
    private final JButton exitButton;
    private List<Integer> topScores;

    public MenuPanel(ActionListener startAction, ActionListener settingsAction, ActionListener exitAction) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null); 

        try {
            backgroundImg = ImageIO.read(new File("assets/images/Background_Menu.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        refreshHighScore();

        startButton = createButton("START GAME", 300, 370);
        settingsButton = createButton("SETTING", 300, 430);
        exitButton = createButton("EXIT", 300, 490);

        startButton.addActionListener(startAction);
        settingsButton.addActionListener(settingsAction);
        exitButton.addActionListener(exitAction);

        add(startButton);
        add(settingsButton);
        add(exitButton);
    }

    public void refreshHighScore() {
        this.topScores = ScoreManager.getHighScores();
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 200, 45);
        btn.setFocusable(false);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(new Color(255, 200, 0));
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImg != null) {
            g2d.drawImage(backgroundImg, 0, 0, 800, 600, null);
        } else {
            g2d.setColor(new Color(20, 20, 60));
            g2d.fillRect(0, 0, 800, 600);
        }

        drawTopScores(g2d);
    }

    private void drawTopScores(Graphics2D g2d) {
        int rectW = 300;
        int rectH = 160;
        int rectX = (800 - rectW) / 2;
        int rectY = 150; // Ã„ÂÃ¡ÂºÂ©y bÃ¡ÂºÂ£ng Ã„â€˜iÃ¡Â»Æ’m xuÃ¡Â»â€˜ng mÃ¡Â»â„¢t chÃƒÂºt (tÃ¡Â»Â« 100 lÃƒÂªn 150)

        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRoundRect(rectX, rectY, rectW, rectH, 20, 20);
        g2d.setColor(Color.ORANGE);
        g2d.drawRoundRect(rectX, rectY, rectW, rectH, 20, 20);

        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        String label = "TOP 3 HIGH SCORES";
        int lx = (800 - g2d.getFontMetrics().stringWidth(label)) / 2;
        g2d.drawString(label, lx, rectY + 35);

        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        if (topScores != null) {
            for (int i = 0; i < 3; i++) {
                String scoreTxt = (i + 1) + ". ";
                if (i < topScores.size()) scoreTxt += topScores.get(i);
                else scoreTxt += "---";
                
                if (i == 0) g2d.setColor(new Color(255, 215, 0));
                else if (i == 1) g2d.setColor(new Color(192, 192, 192));
                else g2d.setColor(new Color(205, 127, 50));
                
                g2d.drawString(scoreTxt, rectX + 60, rectY + 75 + (i * 30));
            }
        }
    }
}
