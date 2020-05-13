package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class A_InteractableObject
{
    int posX, posY, size;
    boolean exploding, destroyed;
    BufferedImage img;
    int explosionStep = 0;
    //Spaceship spaceship;

    public A_InteractableObject(int posX, int posY, int size, BufferedImage img)
    {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }

    public abstract void update();
    public abstract void draw(Graphics gc);
}
