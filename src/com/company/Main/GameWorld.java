package com.company.Main;

import com.company.Handlers.AsteroidHandler;
import com.company.Handlers.EnemyHandler;
import com.company.Services.GameState;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private boolean isPaused = false;


    public void setup() throws IOException
    {
        sessionSystem = SessionSystem.getInstance();
        universe = sessionSystem.getUniverse();
        asteroidHandler = new AsteroidHandler(universe.getComplexity());
        enemyHandler = new EnemyHandler(universe.getComplexity(), universe.getBossEnemy(), universe.getLevel());
        img = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Players/player_ship.png")));
        player = new Player(Utilities.WIDTH /2,Utilities.HEIGHT -(img.getHeight()+20),img.getWidth(), img.getHeight(), img);
        players.add(player);
        worldObjects.add(asteroidHandler.getAsteroids());
        worldObjects.add(players);
        inputSystem = new InputSystem();
        shots = new ArrayList();
        worldObjects.add(shots);
        physicsSystem = new PhysicsSystem(worldObjects);
        int playerSpeed = 7 * (Utilities.WIDTH/1280);
        this.drawScreenState();
        inputSystem.configureInput(graphicSystem, player, playerSpeed, shots);
        graphicSystem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               super.keyPressed(e);
                int key = e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE ? e.getKeyCode(): 0;
                    if (key == KeyEvent.VK_SPACE){
                        isPaused = !isPaused;
                        SessionSystem.getInstance().setGameState(GameState.PAUSED);
                }else if(isPaused && key == KeyEvent.VK_ESCAPE) {
                        System.exit(0);
                        SessionSystem.getInstance().setGameState(GameState.MAINMENU);
                    }
            }
        });

    }

    public void run() throws InterruptedException, IOException {
        enemyHandler.spawnEnemies(universe.getNumberOfEnemies());
        worldObjects.add(enemyHandler.getScreenEnemies());
        worldObjects.add(enemyHandler.getEnemyShots());
        while(!enemyHandler.fightIsOver() && (sessionSystem.getGameState() == GameState.RUNNING || sessionSystem.getGameState() == GameState.PAUSED) )
        {
            if (isPaused){
                this.showPauseMenu();
            }
            double elapsedTime = this.getLoopTime();
            this.updateObjects(elapsedTime);
            this.drawObjects();
            this.handleShots(elapsedTime);
            this.drawStats();
            graphicSystem.redraw();
            try {
                long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                if(timeout > 0)
                Thread.sleep(timeout);
            } finally {
                SessionSystem.getInstance().setPlayerState(player);
                //TODO
            }
        }
    }


    public void setGraphicSystem(GraphicSystem p) { graphicSystem = p; }

    public void drawScreenState(){
        graphicSystem.getG().setColor(Color.BLACK);
        graphicSystem.getG().fillRect(0,0,Utilities.WIDTH,Utilities.HEIGHT);
        graphicSystem.draw(universe);
        asteroidHandler.drawAll(graphicSystem.getG());
        enemyHandler.drawAll(graphicSystem.getG());
        player.draw(graphicSystem.getG());

        for (int i = shots.size() - 1; i >=0 ; i--) {
            PlayerShot shot = (PlayerShot) shots.get(i);
            shot.draw(graphicSystem.getG());
        }
    }


    private void drawObjects(){
        universe.draw(graphicSystem.getG());
        asteroidHandler.drawAll(graphicSystem.getG());
        enemyHandler.drawAll(graphicSystem.getG());
        player.draw(graphicSystem.getG());
    }


    private void updateObjects(double elapsedTime) throws IOException {
        universe.update();
        asteroidHandler.updateAll(elapsedTime);
        enemyHandler.updateAll(elapsedTime);
        player.update(elapsedTime);
        physicsSystem.checkCollisions();
    }

    private void handleShots(double elapsedTime){
        for (int i = shots.size() - 1; i >=0 ; i--) {
            PlayerShot shot = (PlayerShot) shots.get(i);
            if (shot.getPosY() < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update(elapsedTime);
            shot.draw(graphicSystem.getG());
        }
    }

    private void drawStats() throws IOException {
        graphicSystem.getG().drawImage(ImageIO.read((getClass().getClassLoader().getResourceAsStream("Data/frame.png"))),Utilities.WIDTH - 500,-50,null);
        graphicSystem.getG().drawImage(ImageIO.read((getClass().getClassLoader().getResourceAsStream("Players/avatar.png"))),Utilities.WIDTH -448,20,null);
        graphicSystem.getG().setFont(new Font("default", Font.BOLD, 16));
        graphicSystem.getG().setColor(Color.WHITE);
        graphicSystem.getG().drawRect(Utilities.WIDTH - 301, 20, 252,10);
        graphicSystem.getG().setColor(Color.RED);
        graphicSystem.getG().fillRect(Utilities.WIDTH - 300, 21, (int)(250 * ((double)(player.getHealth())/(double)(10*SessionSystem.getInstance().getUniverse().getComplexity()))),8);
        graphicSystem.getG().setColor(Color.WHITE);
        graphicSystem.getG().drawString("Health: " + (Integer.toString(player.getHealth())) + "/" + 10*SessionSystem.getInstance().getUniverse().getComplexity(),  Utilities.WIDTH - 300, 60);
        graphicSystem.getG().drawString("Position X: " + (Integer.toString(player.getPosX())),  10, 20);
        graphicSystem.getG().drawString("Position Y: " + (Integer.toString(player.getPosY())),  10, 45);
        graphicSystem.getG().drawString("Current height: " + (Integer.toString(universe.currentHeight)),  10, 70);
    }

    private void showPauseMenu() throws IOException {
        while (isPaused){
            this.drawScreenState();
            Image background = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Data/background.png")));
            graphicSystem.getG().drawImage(background.getScaledInstance(Utilities.WIDTH,Utilities.HEIGHT, Image.SCALE_SMOOTH),0,0, null);
            Image image = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Data/Pause.png")));
            graphicSystem.getG().drawImage(image,(Utilities.WIDTH/2) - ((BufferedImage) image).getWidth()/2,Utilities.HEIGHT/2 - ((BufferedImage) image).getHeight()/2 , null);
            graphicSystem.getG().setColor(Color.WHITE);
            graphicSystem.getG().drawString("Press ESC to exit the game.",  (Utilities.WIDTH/2) - 500, Utilities.HEIGHT/2);
            graphicSystem.getG().drawString("Press SPACE to continue.",  (Utilities.WIDTH/2) + 300, Utilities.HEIGHT/2);
            graphicSystem.redraw();
            lastLoopTime = System.nanoTime();

        }
        SessionSystem.getInstance().setGameState(GameState.RUNNING);
    }

    private double getLoopTime(){
        long now = System.nanoTime();
        long updateLength = now - lastLoopTime;
        double elapsedTime = updateLength / 1_000_000_000.0;
        lastLoopTime = now;
        return elapsedTime;
    }

}
