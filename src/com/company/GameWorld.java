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
import java.util.List;


public class GameWorld
{
    long lastLoopTime = System.nanoTime();
    final int TARGET_FPS = 30;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    BufferedImage img = null;

    private GraphicSystem graphicSystem;
    private InputSystem inputSystem;
    private SessionSystem sessionSystem;
    private PhysicsSystem physicsSystem;
    private List<List<A_InteractableObject>> worldObjects = new ArrayList<>();
    private AsteroidHandler asteroidHandler;
    private Universe universe;
    private Player player;
    private ArrayList<PlayerShot> shots;
    private List<A_InteractableObject> players = new ArrayList<>();
    private ArrayList<Shot> shots;

    public void setup() throws IOException
    {
        sessionSystem = SessionSystem.getInstance();
        universe = sessionSystem.getUniverse();
        asteroidHandler = new AsteroidHandler(universe.getComplexity());
        img = ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/player_ship.png")));
        player = new Player(100,480,img.getWidth(), img.getHeight(), img);
        players.add(player);
        worldObjects.add(asteroidHandler.getAsteroids());
        worldObjects.add(players);
        inputSystem = new InputSystem();
        shots = new ArrayList();
        physicsSystem = new PhysicsSystem(worldObjects);

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
            graphicSystem.draw(universe);
            asteroidHandler.updateAll();
            asteroidHandler.drawAll(graphicSystem.getG());
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
            physicsSystem.checkCollisions();
            graphicSystem.getG().setColor(Color.RED);
            graphicSystem.getG().drawString((Integer.toString(player.posX)),  400, 400);
            graphicSystem.redraw();
            try {
                long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                if(timeout > 0)
                Thread.sleep(timeout);
            } finally {

            } ;
        }
    }


    public void setGraphicSystem(GraphicSystem p) { graphicSystem = p; }
}
