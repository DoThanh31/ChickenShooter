package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {

    private static final String FILE_NAME = "highscores.dat";
    private static final int MAX_TOP = 3;

    public static void saveHighScore(int score) {
        if (score <= 0) return;
        
        List<Integer> scores = getHighScores();
        scores.add(score);
        
        Collections.sort(scores, Collections.reverseOrder());
        
        if (scores.size() > MAX_TOP) {
            scores = scores.subList(0, MAX_TOP);
        }
        
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_NAME))) {
            dos.writeInt(scores.size());
            for (int s : scores) {
                dos.writeInt(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> getHighScores() {
        List<Integer> scores = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return scores;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
                scores.add(dis.readInt());
            }
        } catch (IOException e) {
        }
        return scores;
    }

    public static int getTopScore() {
        List<Integer> scores = getHighScores();
        return scores.isEmpty() ? 0 : scores.get(0);
    }
}
