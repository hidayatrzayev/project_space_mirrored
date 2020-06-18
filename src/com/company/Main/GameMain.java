package com.company.Main;

import com.company.Services.Utilities;
import com.company.Systems.InputSystem;
import com.company.Systems.SessionSystem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameMain
{
    private GameWorld gameWorld = null;

    public GameMain() throws IOException, InterruptedException {
        GameFrame frame = new GameFrame();

        SessionSystem.getInstance().startNewGame(); //TODO
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        for (int level = 0; level < SessionSystem.getInstance().getNumberOfLevels(); level++) {
            gameWorld = new GameWorld();
            gameWorld.setGraphicSystem(frame.getPanel());
            gameWorld.setup();
            gameWorld.run();
            SessionSystem.getInstance().saveProgress();
            SessionSystem.getInstance().nextLevel();
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