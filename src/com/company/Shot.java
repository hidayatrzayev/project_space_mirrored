package com.company;

import java.awt.*;

public class Shot
{
    public boolean toRemove;
    int posX, posY, speed = 10;
    static final int size = 6;

    public Shot(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
    }

    public void update()
    {
        posY-=speed;
    }

    public void draw(Graphics gc) {
        gc.setColor(Color.RED);
        gc.fillOval(posX, posY, size, size);
    }

}
