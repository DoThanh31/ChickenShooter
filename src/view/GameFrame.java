package view;

import controller.GameController;
import util.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class GameFrame extends JFrame {

    private final GameController gameController;
    private final GamePanel gamePanel;
    private final MenuPanel menuPanel;
    private final SettingsPanel settingsPanel;
    private final CardLayout cardLayout;
    private final JPanel container;
    private Timer gameTimer;

    private final Set<Integer> pressedKeys = new HashSet<>();

    public GameFrame() {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        gameController = new GameController();
        gamePanel = new GamePanel(gameController);
        
        // --- MENU PANEL ---
        menuPanel = new MenuPanel(
            e -> startGame(),
            e -> cardLayout.show(container, "SETTINGS"), // Nút chuyển sang Settings
            e -> System.exit(0)
        );

        // --- SETTINGS PANEL ---
        settingsPanel = new SettingsPanel(
            e -> cardLayout.show(container, "MENU") // Nút Back về Menu
        );

        container.add(menuPanel, "MENU");
        container.add(gamePanel, "GAME");
        container.add(settingsPanel, "SETTINGS");

        setTitle("Chicken Shooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(container);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        showMenu();
        setupGameLoop();
    }

    private void showMenu() {
        cardLayout.show(container, "MENU");
        SoundManager.getInstance().playBGM("assets/sounds/Game_Menu_Music.wav");
    }

    private void startGame() {
        cardLayout.show(container, "GAME");
        gamePanel.requestFocusInWindow();
        SoundManager.getInstance().playBGM("assets/sounds/Game_Music.wav");
        gameController.startGame();
        gameTimer.start();
        setupInput();
    }

    private void setupInput() {
        for (var kl : gamePanel.getKeyListeners()) gamePanel.removeKeyListener(kl);
        for (var ml : gamePanel.getMouseListeners()) gamePanel.removeMouseListener(ml);

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameTimer.stop();
                    menuPanel.refreshHighScore();
                    showMenu();
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    if (gameController.getGameModel().isPause()) gameController.getGameModel().resume();
                    else gameController.getGameModel().pause();
                } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    if (gameController.getGameModel().isGameOver()) {
                        gameController.startGame();
                        SoundManager.getInstance().playBGM("assets/sounds/Game_Music.wav");
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) gameController.setShooting(true);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) gameController.setShooting(false);
            }
        });
    }

    private void processMovement() {
        float px = gameController.getPlayer().getX();
        float py = gameController.getPlayer().getY();
        int speed = 4;
        if (pressedKeys.contains(KeyEvent.VK_LEFT))  gameController.getPlayer().setX(px - speed);
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) gameController.getPlayer().setX(px + speed);
        if (pressedKeys.contains(KeyEvent.VK_UP))    gameController.getPlayer().setY(py - speed);
        if (pressedKeys.contains(KeyEvent.VK_DOWN))  gameController.getPlayer().setY(py + speed);
    }

    private void setupGameLoop() {
        gameTimer = new Timer(16, e -> {
            if (gameController.getGameModel().isPlaying()) {
                processMovement();
                gamePanel.updateBackground();
            }
            gameController.update();
            gamePanel.repaint();
        });
    }
}
