package states;

public class GameStateManager {

    private static GameState currentState = GameState.MENU;

    public static GameState getState() {
        return currentState;
    }

    public static void setState(GameState state) {
        currentState = state;
        System.out.println("STATE -> " + state);
    }
}