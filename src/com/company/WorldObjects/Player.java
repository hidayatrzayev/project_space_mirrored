package com.company.WorldObjects;

import com.company.Services.ComplicatedMesh;
import com.company.Services.GameState;
import com.company.Services.Utilities;
import com.company.Systems.SessionSystem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Player extends A_InteractableObject
{

    private BufferedImage[] explosionAnimations;
    private List<A_InteractableObject> lastAttacker;
    private double velX = 0;
    private double velY = 0;
    private int health;

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
    public void collides(A_InteractableObject a_interactableObject) {
            if (a_interactableObject instanceof Asteroid || a_interactableObject instanceof EnemyShot || a_interactableObject instanceof Enemy) {
                if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                    if (!this.lastAttacker.contains(a_interactableObject)) {
                        this.damage();
                        this.lastAttacker.add(a_interactableObject);
                    }
                }
            }
    }


    public PlayerShot shoot() throws IOException {
        BufferedImage shot = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/shot.png")));
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
            SessionSystem.getInstance().setGameState(GameState.DEAD);
        }
    }

    public int getHealth() {
        return health;
    }


}
