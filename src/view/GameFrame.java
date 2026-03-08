package view;

import javax.swing.*;

public class GameFrame extends JFrame {
    public  GameFrame() {
        setTitle("ChickenShooter");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
