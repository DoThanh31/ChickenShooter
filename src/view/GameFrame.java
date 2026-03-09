package view;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GamePanel gamePanel=new GamePanel();
    public  GameFrame() {
        add(gamePanel);
        pack();
        setTitle("ChickenShooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
