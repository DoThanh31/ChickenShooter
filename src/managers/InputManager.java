package managers;

import states.GameState;
import states.GameStateManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputManager extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER) {
            if (GameStateManager.getState() == GameState.MENU) {
                GameStateManager.setState(GameState.PLAY);
            } else if (GameStateManager.getState() == GameState.PAUSE) {
                GameStateManager.setState(GameState.PLAY);
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            if (GameStateManager.getState() == GameState.PLAY) {
                GameStateManager.setState(GameState.PAUSE);
            }
        }
    }
}