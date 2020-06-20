package com.company.Main;

import com.company.Services.Utilities;
import com.company.Systems.BackgroundMusicPlayer;
import com.company.Systems.InputSystem;
import com.company.Systems.SessionSystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GameMain
{
    private GameWorld gameWorld = null;

    public GameMain() throws IOException, InterruptedException {
        JWindow window = new JWindow();
        Image splashScreen = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Data/loading.jpg"))).getScaledInstance(Utilities.WIDTH/3,Utilities.HEIGHT/3, Image.SCALE_SMOOTH);
        window.getContentPane().add(new JLabel("", new ImageIcon(splashScreen), SwingConstants.CENTER));
        window.setBounds((Utilities.WIDTH/2) - (Utilities.WIDTH/3)/2, (Utilities.HEIGHT/2)-(Utilities.HEIGHT/3)/2, Utilities.WIDTH/3, Utilities.HEIGHT/3);
        window.setVisible(true);
        GameFrame frame = new GameFrame();
        (new Thread((new BackgroundMusicPlayer()))).start();
        SessionSystem.getInstance().startNewGame(); //TODO
        SessionSystem.getInstance().loadProgress(); //TODO
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        for (int level = SessionSystem.getInstance().getLevel(); level < SessionSystem.getInstance().getNumberOfLevels(); level++) {
            gameWorld = new GameWorld();
            gameWorld.setGraphicSystem(frame.getPanel());
            gameWorld.setup();
            if (!frame.isVisible()) {
                window.setVisible(false);
                frame.setVisible(true);
            }
            gameWorld.run();
            SessionSystem.getInstance().nextLevel();
            SessionSystem.getInstance().saveProgress();
        }
        //TODO
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        setResolution();
        new GameMain();
    }

    public static void setResolution(){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Utilities.setWIDTH(gd.getDisplayMode().getWidth());
        Utilities.setHEIGHT(gd.getDisplayMode().getHeight());
    }

}