package com.company;

import java.io.IOException;

public class GameMain
{
    private GameWorld gameWorld = null;

    public GameMain() throws IOException, InterruptedException {
        GameFrame frame = new GameFrame();
        frame.setVisible(true);
        gameWorld = new GameWorld();
        gameWorld.setGraphicSystem(frame.getPanel());
        gameWorld.setup();
        gameWorld.run();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new GameMain();
    }
}