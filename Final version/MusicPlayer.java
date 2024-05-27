import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private static boolean musicThreadRunning = false;
    private static Clip backgroundMusic;

    public static synchronized void startMusic() {
        if (!musicThreadRunning) {
            Thread musicThread = new Thread(() -> {
                playBackgroundMusic();
            });
            musicThread.start();
            musicThreadRunning = true;
        }
    }

    private static void playBackgroundMusic() {
        try {
            File musicFile = new File("Trouble_Maker.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
        musicThreadRunning = false;
    }
}
