package com.company;

import java.awt.image.BufferedImage;

public class BossEnemy extends Enemy {

    public BossEnemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed, int health) {
        super(posX, posY, sizeX, sizeY, img, speed, health);
    }

    @Override
    public void update() {
        super.update();
        if (posY > 720 / 4 || (posY == 0 && direction < 0)) {
            this.direction *= -1;
        }
    }

    @Override
    public boolean collides(A_InteractableObject other) {
        return super.collides(other);
    }
}
