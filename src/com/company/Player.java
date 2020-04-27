package com.company;

import java.awt.*;

public class Player extends A_InteractableObject
{
    public Player(int posX, int posY, int size, Image img)
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
            gc.setColor(Color.BLACK);
            gc.fillRect(100, 100, 200, 200);
        }
    }
}
