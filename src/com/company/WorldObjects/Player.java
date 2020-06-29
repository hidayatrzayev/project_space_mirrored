package com.company.WorldObjects;

import com.company.Services.ComplicatedMesh;
import com.company.Services.GameState;
import com.company.Services.Utilities;
import com.company.Systems.BackgroundMusicPlayer;
import com.company.Systems.SessionSystem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Player extends A_InteractableObject
{

    private BufferedImage[] explosionAnimations;
    private List<A_InteractableObject> lastAttacker;
    private double velX = 0;
    private double velY = 0;
    private int health;
    private static final String musicFile = "resources/Audio/ShipExplosion.wav";
    private static final String shotMusicFile = "resources/Audio/PlayerShot.wav";
    private boolean playingExplosion = false;

    public Player(int posX, int posY, int sizeX,int sizeY, BufferedImage img) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.explosionAnimations = Utilities.explosionAnimations;
        this.mesh = new ComplicatedMesh(img);
        this.lastAttacker = new ArrayList<>();
        this.health = 10* SessionSystem.getInstance().getUniverse().getComplexity();
    }

    @Override
    public void update(double elapsedTime)
    {
        posX += velX;
        posY += velY;
    }

    @Override
    public void draw(Graphics gc)
    {
        if(exploding)
        {
            if(explosionStep != 48) {
                gc.drawImage(this.explosionAnimations[explosionStep], this.posX, this.posY, null);
                this.explosionStep++;
            }else{
                this.destroyed = true;
            }
        }
        else
        {
            gc.drawImage(img, posX, posY, null);
        }
    }

    @Override
    public synchronized void collides(A_InteractableObject a_interactableObject) throws ExecutionException, InterruptedException {
            if (a_interactableObject instanceof Asteroid || a_interactableObject instanceof EnemyShot) {
                if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                    if (!this.lastAttacker.contains(a_interactableObject)) {
                        this.damage();
                        this.lastAttacker.add(a_interactableObject);
                    }
                }
            }else if(a_interactableObject instanceof Enemy){
                if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                    this.health = 1;
                    this.damage();
                }
            }
    }


    public PlayerShot shoot() throws IOException {
        BufferedImage shot = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/shot.png")));
        new Thread((new BackgroundMusicPlayer(shotMusicFile))).start();
        return new PlayerShot((posX + 94 / 2) - 26, posY - 20, shot.getWidth(), shot.getHeight(), shot);
    }
    public void setPosX(int newPosX)
    {
        this.posX = newPosX;
    }
    public void setPosY(int newPosY)
    {
        this.posY = newPosY;
    }

    public void setVelX(double velX)
    {
        this.velX = velX;
    }

    public void setVelY(double velY)
    {
        this.velY = velY;
    }

    @Override
    public String toString() {
        return "{" +
                "\"posX\":\"" + posX + '\"' +
                ",\"posY\":\"" + posY + '\"' +
                ",\"sizeX\":\"" + sizeX + '\"' +
                ",\"sizeY\":\"" + sizeY+ '\"' +
                '}';
    }

    public void damage() {
        this.health--;
        if (this.health == 0) {
            this.exploding = true;
            if (!playingExplosion){
                new Thread((new BackgroundMusicPlayer(musicFile))).start();
                playingExplosion = true;
            }
            SessionSystem.getInstance().setGameState(GameState.DEAD);

        }
    }

    public int getHealth() {
        return health;
    }


}
