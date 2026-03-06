package core;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle("Chicken Shooter");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

       // add(new game.core.GamePanel());

        setVisible(true);
    }
}