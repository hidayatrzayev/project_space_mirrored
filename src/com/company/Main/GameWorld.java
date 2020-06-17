package com.company.Main;

import com.company.Handlers.AsteroidHandler;
import com.company.Handlers.EnemyHandler;
import com.company.Services.Utilities;
import com.company.Systems.GraphicSystem;
import com.company.Systems.InputSystem;
import com.company.Systems.PhysicsSystem;
import com.company.Systems.SessionSystem;
import com.company.WorldObjects.A_InteractableObject;
import com.company.WorldObjects.Player;
import com.company.WorldObjects.PlayerShot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    private ArrayList<A_InteractableObject> shots;
    private ArrayList<A_InteractableObject> enemies;
    private List<A_InteractableObject> players = new ArrayList<>();
    private EnemyHandler enemyHandler;


    public void setup() throws IOException
    {
        sessionSystem = SessionSystem.getInstance();
        universe = sessionSystem.getUniverse();
        asteroidHandler = new AsteroidHandler(universe.getComplexity());
        enemyHandler = new EnemyHandler(universe.getComplexity(), universe.getBossEnemy());
        img = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Players/player_ship.png")));
        player = new Player(Utilities.WIDTH /2,Utilities.HEIGHT -(img.getHeight()+20),img.getWidth(), img.getHeight(), img);
        players.add(player);
        worldObjects.add(asteroidHandler.getAsteroids());
        worldObjects.add(players);
        inputSystem = new InputSystem();
        shots = new ArrayList();
        worldObjects.add(shots);
        physicsSystem = new PhysicsSystem(worldObjects);
        int playerSpeed = 7;

        inputSystem.configureInput(graphicSystem, player, playerSpeed, shots);


    }

    public void run() throws InterruptedException, IOException {
        enemyHandler.spawnEnemies(universe.getNumberOfEnemies());
        enemyHandler.spawnBossEnemy();
        worldObjects.add(enemyHandler.getScreenEnemies());
        worldObjects.add(enemyHandler.getEnemyShots());
        while(true)
        {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);
            universe.update();
            //graphicSystem.draw(universe);
            universe.draw(graphicSystem.getG());

            enemyHandler.shootRandomly();

            asteroidHandler.updateAll();
            asteroidHandler.drawAll(graphicSystem.getG());

            enemyHandler.updateEnemies();
            enemyHandler.updateEnemyShots();

            enemyHandler.drawEnemies(graphicSystem.getG());
            enemyHandler.drawEnemyShots(graphicSystem.getG());

            player.update();
            player.draw(graphicSystem.getG());
            for (int i = shots.size() - 1; i >=0 ; i--) {
                PlayerShot shot = (PlayerShot) shots.get(i);
                if (shot.getPosY() < 0 || shot.toRemove) {
                    shots.remove(i);
                    continue;
                }
                shot.update();
                shot.draw(graphicSystem.getG());
            }
            physicsSystem.checkCollisions();

            graphicSystem.getG().setColor(Color.WHITE);
            graphicSystem.getG().drawString("Position X: " + (Integer.toString(player.getPosX())),  10, 20);
            graphicSystem.getG().drawString("Position Y: " + (Integer.toString(player.getPosY())),  10, 45);
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
