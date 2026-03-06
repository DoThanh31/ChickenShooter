//package game.core;
//
//import states.GameState;
//import states.GameStateManager;
//
//import javax.swing.JPanel;
//import java.awt.Graphics;
//import java.awt.Color;
//
//public class GamePanel extends JPanel implements Runnable {
//
//    private Thread gameThread;
//    private boolean running = true;
//
//    public GamePanel() {
//        setFocusable(true);
//        startGameLoop();
//    }
//
//    private void startGameLoop() {
//        gameThread = new Thread(this);
//        gameThread.start();
//    }
//
//    @Override
//    public void run() {
//        while (running) {
//            update();
//            repaint();
//
//            try {
//                Thread.sleep(16); // ~60 FPS
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void update() {
//        // Logic theo state
//        if (GameStateManager.getState() == GameState.PLAY) {
//            // update game
//        }
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        GameState state = GameStateManager.getState();
//
//        if (state == GameState.MENU) {
//            drawMenu(g);
//        } else if (state == GameState.PLAY) {
//            drawPlay(g);
//        } else if (state == GameState.PAUSE) {
//            drawPause(g);
//        }
//    }
//
//    private void drawMenu(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.drawString("MENU - Press ENTER to Play", 300, 300);
//    }
//
//    private void drawPlay(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.drawString("PLAYING - Press ESC to Pause", 280, 300);
//    }
//
//    private void drawPause(Graphics g) {
//        g.setColor(Color.RED);
//        g.drawString("PAUSED - Press ENTER to Resume", 260, 300);
//    }
//}