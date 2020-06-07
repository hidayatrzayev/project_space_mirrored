package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GameWorld
{
    long lastLoopTime = System.nanoTime();
    final int TARGET_FPS = 30;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    BufferedImage img = null;

    private GraphicSystem graphicSystem;
    private InputSystem inputSystem;

    private Player player;
    private ArrayList<PlayerShot> shots;

    public void setup() throws IOException
    {
        img = ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/player_ship.png")));
        player = new Player(100,480,0, 0, img);
        inputSystem = new InputSystem();
        shots = new ArrayList();

        graphicSystem.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                player.setPosX(e.getX() - 47);
            }
        });

        graphicSystem.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                player.setPosX(e.getX() - 47);
            }
        });
        graphicSystem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(shots.size() < 20) {
                    try {
                        shots.add(player.shoot());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });



    }

    public void run() throws InterruptedException {
        while(true)
        {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);

            graphicSystem.draw();
            player.draw(graphicSystem.getG());
            for (int i = shots.size() - 1; i >=0 ; i--) {
                PlayerShot shot = shots.get(i);
                if (shot.posY < 0 || shot.toRemove) {
                    shots.remove(i);
                    continue;
                }
                shot.update();
                shot.draw(graphicSystem.getG());
            }
            graphicSystem.getG().setColor(Color.RED);
            graphicSystem.getG().drawString((Integer.toString(player.posX)),  400, 400);
            graphicSystem.redraw();
            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } finally {

            } ;
        }
    }


    public void setGraphicSystem(GraphicSystem p) { graphicSystem = p; }
}
