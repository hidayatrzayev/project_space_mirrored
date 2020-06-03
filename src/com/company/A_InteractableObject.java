package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class A_InteractableObject
{
    int posX, posY, sizeX, sizeY;
    boolean exploding, destroyed;
    BufferedImage img;
    int explosionStep = 0;
    //Spaceship spaceship;

    public A_InteractableObject(int posX, int posY, int sizeX, int sizeY, BufferedImage img)
    {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.img = img;
    }

    public abstract void update();
    public abstract void draw(Graphics gc);

    public Rectangle getBounds()
    {
        return new Rectangle(posX, posY, sizeX - 5, sizeY - 5);
    }
}
