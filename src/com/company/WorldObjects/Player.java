package com.company.WorldObjects;

import com.company.Services.ComplicatedMesh;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.company.Services.Utilities.getAnimations;

public class Player extends A_InteractableObject
{

    private BufferedImage[] explosionAnimations;
    private int lastAttacker;
    private double velX = 0;
    private double velY = 0;

    public Player(int posX, int posY, int sizeX,int sizeY, BufferedImage img) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.explosionAnimations = getAnimations(51,51,6,8,ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/explosion.png"))));
        this.mesh = new ComplicatedMesh(img);
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
        if(this.getBounds().intersects(a_interactableObject.getBounds())) {
            if (a_interactableObject instanceof Asteroid || a_interactableObject instanceof EnemyShot || a_interactableObject instanceof  Enemy) {
                if (!a_interactableObject.isDestroyed() && this.lastAttacker != a_interactableObject.hashCode()) {
                    this.exploding = true;
                    this.lastAttacker = a_interactableObject.hashCode();
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
}
