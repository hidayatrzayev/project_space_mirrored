package com.company;

import java.awt.*;

public abstract class A_InteractableObject
{
    int posX, posY, size;
    boolean exploding, destroyed;
    Image img;
    int explosionStep = 0;

    public A_InteractableObject(int posX, int posY, int size, Image img)
    {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }

    public abstract void update();
    public abstract void draw(Graphics gc);
}
