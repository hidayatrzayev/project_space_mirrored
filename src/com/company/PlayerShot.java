package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerShot extends A_InteractableObject
{
    public boolean toRemove;
    int speed = 5;
    int animationStep = 0;
    int animationLimiter = 0;
    static final int ANIMATION_W = 52;
    static final int ANIMATION_ROWS = 1;
    static final int ANIMATION_COL = 3;
    static final int ANIMATION_H = 52;
    static final int ANIMATION_STEPS = 12;

    public PlayerShot(int posX, int posY, int sizeX, int sizeY, BufferedImage img)
    {
        super(posX, posY, sizeX, sizeY, img);
    }

    @Override
    public void update()
    {

        if (animationStep >= ANIMATION_STEPS)
        {
            animationStep = 0;
        }
        else
        {
            animationStep++;
        }

        //animationStep = animationStep >= ANIMATION_STEPS ? 0 : animationStep++;
        posY-=speed;
    }

    @Override
    public void draw(Graphics gc)
    {
        gc.drawImage(img.getSubimage(0 + (ANIMATION_W * (animationStep/4)), 0, 52, 52),  posX, posY, null);
    }
}
