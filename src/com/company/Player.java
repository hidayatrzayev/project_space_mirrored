package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends A_InteractableObject
{
    public Player(int posX, int posY, int sizeX,int sizeY, BufferedImage img)
    {
        super(posX, posY, sizeX, sizeY, img);
    }

    public void update()
    {

    }

    public void draw(Graphics gc)
    {
        if(exploding)
        {

        }
        else
        {
            gc.drawImage(img, posX, posY, null);
        }
    }
/*
    public Shot shoot() {
        return new Shot(posX + 94 / 2 - Shot.size / 2, posY - Shot.size);

    }
 */
    public PlayerShot shoot() throws IOException {
        return new PlayerShot((posX + 94 / 2) - 26, posY - 20, 0, 0, ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/shot.png"))));
    }
    public void setPosX(int newPosX)
    {
        this.posX = newPosX;
    }
    public void setPosY(int newPosY)
    {
        this.posY = newPosY;
    }
}