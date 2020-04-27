package com.company;

import java.awt.*;
import java.util.Random;

public class Universe {
    private static final Random RAND = new Random();
    int posX, posY;
    private int h, w, r, g, b;
    private double opacity;

    public Universe() {
        posX = RAND.nextInt(1280);
        posY = 0;
        w = RAND.nextInt(5) + 1;
        h =  RAND.nextInt(5) + 1;
        r = RAND.nextInt(100) + 150;
        g = RAND.nextInt(100) + 150;
        b = RAND.nextInt(100) + 150;
        opacity = RAND.nextFloat();
        if(opacity < 0) opacity *=-1;
        if(opacity > 0.5) opacity = 0.5;
    }

    public void draw(Graphics gc) {
        if(opacity > 0.8) opacity-=0.01;
        if(opacity < 0.1) opacity+=0.01;
        gc.setColor(Color.WHITE);
        gc.fillOval(posX, posY, w, h);
        posY+=20;
    }
}
