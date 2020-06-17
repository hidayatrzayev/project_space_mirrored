package com.company.WorldObjects;

import com.company.Services.CircleMesh;
import com.company.Services.Utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyShot extends PlayerShot {

    public EnemyShot(int posX, int posY, int sizeX, int sizeY, BufferedImage img) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.mesh = new CircleMesh(10,10,25,25);
    }

    @Override
    public void update(double elapsedTime) {
        if (!this.exploding) {
            if (animationStep >= ANIMATION_STEPS) {
                animationStep = 0;
            } else {
                animationStep++;
            }

            posY += speed;
            if (posY > Utilities.HEIGHT) {
                toRemove = true;
            }
        }
    }

    @Override
    public void collides(A_InteractableObject a_interactableObject) {
        if(this.getBounds().intersects(a_interactableObject.getBounds())) {
            if (a_interactableObject instanceof Player || a_interactableObject instanceof Asteroid ) {
                this.destroyed = true;
                this.toRemove = true;
            }
        }
    }

}
