package view;

import util.SoundManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class SettingsPanel extends JPanel {

    private BufferedImage backgroundImg;
    private final JSlider masterVolumeSlider;
    private final JButton backButton;

    public SettingsPanel(ActionListener backAction) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);

        try {
            backgroundImg = ImageIO.read(new File("assets/images/Background_Menu.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Font labelFont = new Font("Arial", Font.BOLD, 24);
        Color textColor = Color.WHITE;

        JLabel masterVolumeLabel = new JLabel("MASTER VOLUME");
        masterVolumeLabel.setBounds(300, 200, 200, 30); // Đưa lên cao hơn
        masterVolumeLabel.setFont(labelFont);
        masterVolumeLabel.setForeground(textColor);
        add(masterVolumeLabel);

        masterVolumeSlider = createVolumeSlider((int)(SoundManager.getInstance().getMasterVolume() * 100));
        masterVolumeSlider.setBounds(250, 240, 300, 50);
        masterVolumeSlider.addChangeListener(e -> {
            float vol = masterVolumeSlider.getValue() / 100f;
            SoundManager.getInstance().setMasterVolume(vol);
        });
        add(masterVolumeSlider);

        backButton = new JButton("BACK");
        backButton.setBounds(300, 350, 200, 50);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setFocusable(false);
        backButton.setBackground(new Color(255, 200, 0));
        backButton.addActionListener(backAction);
        add(backButton);
    }

    private JSlider createVolumeSlider(int initialValue) {
        JSlider slider = new JSlider(0, 100, initialValue);
        slider.setOpaque(false);
        slider.setMajorTickSpacing(25);
        slider.setPaintTicks(true);
        return slider;
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
    }
}
