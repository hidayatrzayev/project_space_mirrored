package com.company.WorldObjects;

import com.company.Services.CircleMesh;
import com.company.Services.Utilities;
import com.company.Shootings.ShootStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyShot extends A_InteractableObject {

    private double velocityX;
    private double velocityY;
    private boolean toRemove;

//    public EnemyShot(int posX, int posY, int sizeX, int sizeY, BufferedImage img) throws IOException {
//        super(posX, posY, sizeX, sizeY, img);
//        this.mesh = new CircleMesh(10, 10, 25, 25);
//    }

    public EnemyShot(int posX, int posY, int sizeX, int sizeY, double velocityX, double velocityY) {
        super(posX, posY, sizeX, sizeY);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.toRemove = false;
        this.mesh = new CircleMesh(0, 0, ShootStrategy.SHOT_SIZE, ShootStrategy.SHOT_SIZE);
    }

    @Override
    public void update(double elapsedTime) {
        if (!this.exploding) {
            posX += velocityX * elapsedTime;
            posY += velocityY * elapsedTime;
            if (posY > Utilities.HEIGHT) {
                toRemove = true;
            }
        }
    }

    @Override
    public void draw(Graphics gc) {
        if (!exploding) {
            gc.setColor(Color.RED);
            gc.fillOval(posX, posY, sizeX, sizeY);
        } else {
            if(explosionStep != 48) {
                gc.drawImage(this.explosionAnimations[explosionStep], this.posX, this.posY, null);
                this.explosionStep++;
            }else{
                this.destroyed = true;
                this.toRemove = true;
            }
        }
    }

    @Override
    public void collides(A_InteractableObject a_interactableObject) {
        if (a_interactableObject instanceof Player || a_interactableObject instanceof Asteroid) {
            if(Utilities.distance(this.posX, this.posY,
                    a_interactableObject.getPosX(), a_interactableObject.getPosY()) < ShootStrategy.SHOT_SIZE * 4) {
                if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                    this.destroyed = true;
                    this.toRemove = true;
                }
            }
        }
    }

    public boolean isToRemove() {
        return toRemove;
    }
}
