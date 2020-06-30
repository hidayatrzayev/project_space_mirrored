package com.company.Main;

import com.company.Services.GameState;
import com.company.Services.Utilities;
import com.company.Systems.BackgroundMusicPlayer;
import com.company.Systems.InputSystem;
import com.company.Systems.SessionSystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.GenericArrayType;
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
        (new Thread((new BackgroundMusicPlayer("resources/Audio/music.wav")))).start();
        SessionSystem.getInstance().setGameState(GameState.MAINMENU);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        int level = SessionSystem.getInstance().getLevel();
        while(SessionSystem.getInstance().getGameState() != GameState.EXIT)
        {
            Thread.sleep(0,1);
            if(SessionSystem.getInstance().getGameState() == GameState.MAINMENU)
            {
                if (!frame.isVisible())
                {
                    window.setVisible(false);
                    frame.setVisible(true);
                }
            }
            while (level <= SessionSystem.getInstance().getNumberOfLevels() && SessionSystem.getInstance().getGameState() == GameState.RUNNING) {

                gameWorld = new GameWorld();
                gameWorld.setGraphicSystem(frame.getPanel());
                gameWorld.setup();
                frame.changePanel();
                gameWorld.run();
                if (SessionSystem.getInstance().getGameState() == GameState.RUNNING) {
                    SessionSystem.getInstance().nextLevel();
                    SessionSystem.getInstance().saveProgress();
                    level++;
                }
                //TODO OPEN A MENU SPECIFIED IN SESSIONSYSTEM (INTERMEDIATE MENU)
                //TODO e.g menuHandler.open(SessionSystem.getInstance().getGameState())....//TODO
                //TODO BUT MEANWHILE REPEAT LEVEL:
                if (SessionSystem.getInstance().getGameState() == GameState.DEAD) {  //TODO DELETE THIS
                    level--;                                                        //TODO DELETE THIS
                    SessionSystem.getInstance().setGameState(GameState.RUNNING);    //TODO DELETE THIS
                }                                                                   //TODO DELETE THIS
            }
            if (SessionSystem.getInstance().getGameState() == GameState.RUNNING) {
                SessionSystem.getInstance().setGameState(GameState.FINISH);
                //TODO IF PLAYER COMPLETED ALL LEVELS --> DO SOMETHING
            } else if (SessionSystem.getInstance().getGameState() == GameState.DEAD) {
                //TODO IF PLAYER DECIDED TO EXIT AFTER THE DEATH --> OPEN MAINMENU
            }
        }
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