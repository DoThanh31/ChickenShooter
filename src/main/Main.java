package main;

import util.SoundManager;
import view.GameFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        setupSounds();

        SwingUtilities.invokeLater(() -> {
            new GameFrame();
        });
    }

    private static void setupSounds() {
        SoundManager sm = SoundManager.getInstance();
        
        sm.load("shoot", "assets/sounds/plane_bullet_sound.wav");
        sm.load("chicken_die", "assets/sounds/chicken_die_sound.wav");
        sm.load("boss_spawn", "assets/sounds/Boss_Music.wav"); // Âm thanh Boss xuất hiện
        sm.load("game_over", "assets/sounds/gameover_music_sound.wav");
        sm.load("win", "assets/sounds/victory_music_sound.wav");

    }
}
