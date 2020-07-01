package com.company.Systems;

import javax.sound.sampled.*;
import java.io.File;

public class BackgroundMusicPlayer implements Runnable
{
    private String musicFile;

    public BackgroundMusicPlayer(String musicFile)
    {
        this.musicFile = musicFile;
    }

    private void playSound()
    {
        try
        {
            File soundFile = new File(this.musicFile);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = ais.getFormat();
            DataLine.Info info  = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
            clip.start();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void run()
    {
        playSound();
    }

}