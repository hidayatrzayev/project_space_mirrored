package com.company;

import java.awt.image.BufferedImage;

public class EnemyShot extends PlayerShot {

    public EnemyShot(int posX, int posY, int sizeX, int sizeY, BufferedImage img) {
        super(posX, posY, sizeX, sizeY, img);
    }

    @Override
    public void update() {
        if (animationStep >= ANIMATION_STEPS) {
            animationStep = 0;
        } else {
            animationStep++;
        }

        posY += speed;
        if (posY > 1280) {
            toRemove = true;
        }
    }
}
