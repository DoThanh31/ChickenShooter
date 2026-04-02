package model;

import util.ScoreManager;

/**
 * GameModel - Trạng thái tổng thể của game
 */
public class GameModel {

    public enum Phase { PLAYING, PAUSE, LEVELUP, WIN, LOSE }

    private int   score;
    private int   level;      // 1 → 5
    private Phase phase;
    private int   highScore; // Top 1 Score
    
    private int levelUpTimer = 0; // Đếm ngược thời gian hiển thị màn hình Level Up

    public static final int MAX_LEVEL = 5;

    public GameModel() {
        this.highScore = ScoreManager.getTopScore();
        reset();
    }

    // ── Logic chính ───────────────────────────────────────────

    public void addScore(int value) {
        score += value;
        if (score > highScore) {
            highScore = score;
        }
    }

    public void nextLevel() {
        if (level < MAX_LEVEL) {
            level++;
            phase = Phase.LEVELUP; // Chuyển sang phase LEVELUP
            levelUpTimer = 120;    // Hiển thị trong khoảng 2 giây (60fps * 2)
            System.out.println("Model Level Up to: " + level);
        } else {
            setWin();
        }
    }

    /** Cập nhật logic phase (gọi trong GameController) */
    public void update() {
        if (phase == Phase.LEVELUP) {
            if (levelUpTimer > 0) {
                levelUpTimer--;
            } else {
                phase = Phase.PLAYING; // Quay lại chơi sau khi hết thời gian chờ
            }
        }
    }

    private void setWin() {
        phase = Phase.WIN;
        ScoreManager.saveHighScore(score);
    }

    public void setLose() {
        phase = Phase.LOSE;
        ScoreManager.saveHighScore(score);
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

    public void reset() {
        score = 0;
        level = 1;
        phase = Phase.PLAYING;
        levelUpTimer = 0;
        this.highScore = ScoreManager.getTopScore();
    }

    // ── Helper ─────────────────────────────────

    public boolean isPlaying() { return phase == Phase.PLAYING; }
    public boolean isPause() { return phase == Phase.PAUSE; }
    public boolean isLevelUp() { return phase == Phase.LEVELUP; }
    public boolean isGameOver() { return phase == Phase.WIN || phase == Phase.LOSE; }

    public int getScore() { return score; }
    public int getHighScore() { return highScore; }
    public int getLevel() { return level; }
    public Phase getPhase() { return phase; }
}
