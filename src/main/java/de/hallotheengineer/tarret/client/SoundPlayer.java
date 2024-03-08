package de.hallotheengineer.tarret.client;

import de.hallotheengineer.tarret.Tarret;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {
    public static synchronized void playSound(String fileName) {
        if (fileName == null) return;
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                InputStream resource = MinecraftClient.getInstance().getResourceManager().open(new Identifier(Tarret.MOD_ID, "sound/"+fileName));
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(resource));

                clip.open(inputStream);
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(6.0206f);

                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
