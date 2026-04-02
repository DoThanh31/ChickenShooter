package model;

import util.ScoreManager;

public class GameModel {

    public enum Phase { PLAYING, PAUSE, LEVELUP, WIN, LOSE }

    private int   score;
    private int   level;      // 1 â†’ 5
    private Phase phase;
    private int   highScore; // Top 1 Score
    
    private int levelUpTimer = 0; // Äáº¿m ngÆ°á»£c thá»i gian hiá»ƒn thá»‹ mÃ n hÃ¬nh Level Up

    public static final int MAX_LEVEL = 5;

    public GameModel() {
        this.highScore = ScoreManager.getTopScore();
        reset();
    }


    public void addScore(int value) {
        score += value;
        if (score > highScore) {
            highScore = score;
        }
    }

    public void nextLevel() {
        if (level < MAX_LEVEL) {
            level++;
            phase = Phase.LEVELUP; // Chuyá»ƒn sang phase LEVELUP
            levelUpTimer = 120;    // Hiá»ƒn thá»‹ trong khoáº£ng 2 giÃ¢y (60fps * 2)
            System.out.println("Model Level Up to: " + level);
        } else {
            setWin();
        }
    }

    public void update() {
        if (phase == Phase.LEVELUP) {
            if (levelUpTimer > 0) {
                levelUpTimer--;
            } else {
                phase = Phase.PLAYING; // Quay láº¡i chÆ¡i sau khi háº¿t thá»i gian chá»
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


    public boolean isPlaying() { return phase == Phase.PLAYING; }
    public boolean isPause() { return phase == Phase.PAUSE; }
    public boolean isLevelUp() { return phase == Phase.LEVELUP; }
    public boolean isGameOver() { return phase == Phase.WIN || phase == Phase.LOSE; }

    public int getScore() { return score; }
    public int getHighScore() { return highScore; }
    public int getLevel() { return level; }
    public Phase getPhase() { return phase; }
}
