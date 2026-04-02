package util;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static SoundManager instance;
    private final Map<String, Clip> sounds;
    private Clip bgm;
    
    private float masterVolume = 0.8f; // Má»™t Ã¢m lÆ°á»£ng tá»•ng cho táº¥t cáº£

    private SoundManager() {
        sounds = new HashMap<>();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void load(String key, String path) {
        try {
            File soundFile = new File(path);
            if (!soundFile.exists()) return;
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            sounds.put(key, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(String key) {
        Clip clip = sounds.get(key);
        if (clip != null) {
            applyVolume(clip, masterVolume);
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void playBGM(String path) {
        if (bgm != null && bgm.isRunning()) bgm.stop();
        try {
            File soundFile = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            bgm = AudioSystem.getClip();
            bgm.open(audioIn);
            applyVolume(bgm, masterVolume);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
            bgm.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBGM() {
        if (bgm != null) bgm.stop();
    }

    public void setMasterVolume(float volume) {
        this.masterVolume = volume;
        if (bgm != null) {
            applyVolume(bgm, masterVolume);
        }
    }

    private void applyVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(Math.max(volume, 0.0001)) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public float getMasterVolume() { return masterVolume; }
}
