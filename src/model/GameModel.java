package model;


/**
 * GameModel - Trạng thái tổng thể của game
 */
public class GameModel {

    public enum Phase { PLAYING, PAUSE, LEVELUP, WIN, LOSE }

    private int   score;
    private int   level;      // 1 → 5
    private Phase phase;

    public static final int MAX_LEVEL = 5;

    public GameModel() {
        reset();
    }

    // ── Logic chính ───────────────────────────────────────────

    public void addScore(int value) {
        score += value;
    }

    public void nextLevel() {
        level++;
        if (level > MAX_LEVEL) {
            phase = Phase.WIN;
        } else {
            phase = Phase.LEVELUP;
        }
    }

    public void pause() {
        if (phase == Phase.PLAYING) {
            phase = Phase.PAUSE;
        }
    }

    public void resume() {
        if (phase == Phase.PAUSE) {
            phase = Phase.PLAYING;
        }
    }

    public void setLose() {
        phase = Phase.LOSE;
    }

    public void reset() {
        score = 0;
        level = 1;
        phase = Phase.PLAYING;
    }

    // ── Helper (rất nên có) ─────────────────────────────────

    public boolean isPlaying() {
        return phase == Phase.PLAYING;
    }

    public boolean isPause() {
        return phase == Phase.PAUSE;
    }

    public boolean isGameOver() {
        return phase == Phase.WIN || phase == Phase.LOSE;
    }

    public boolean isBossLevel() {
        return level >= 3;
    }

    // ── Getter ───────────────────────────────────────────────

    public int getScore() { return score; }
    public int getLevel() { return level; }
    public Phase getPhase() { return phase; }
}