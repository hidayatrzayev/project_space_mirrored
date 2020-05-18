package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends A_InteractableObject
{
    public Player(int posX, int posY, int size, BufferedImage img)
    {
        super(posX, posY, size, img);
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

    public Shot shoot() {
        return new Shot(posX + 94 / 2 - Shot.size / 2, posY - Shot.size);
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
