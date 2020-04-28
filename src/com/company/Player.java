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

    public void setPosX(int newPosX)
    {
        this.posX = newPosX;
    }
}
