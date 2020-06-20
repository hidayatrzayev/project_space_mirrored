package com.company.WorldObjects;

public class Star
{
    public int posX;
    public int posY;

    public Star(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
    }

    public void update()
    {

    }

    public void setPos(int x, int y)
    {
        posX = x;
        posY = y;
    }

}
