package com.company.WorldObjects;

import com.company.Services.CircleMesh;
import com.company.Services.Utilities;
import com.company.Shootings.ShootStrategy;
import com.company.Systems.BackgroundMusicPlayer;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class EnemyShot extends A_InteractableObject {

    private double velocityX;
    private double velocityY;
    private boolean toRemove;
    private static final String musicFile = "resources/Audio/ShotExplosion.wav";
    private boolean playingExplosion = false;

    public EnemyShot(int posX, int posY, int sizeX, int sizeY, double velocityX, double velocityY) throws IOException {
        super(posX, posY, sizeX, sizeY);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.toRemove = false;
        this.explosionAnimations = Utilities.explosionAnimations;
        this.mesh = new CircleMesh(0, 0, ShootStrategy.SHOT_SIZE, ShootStrategy.SHOT_SIZE);
    }

    @Override
    public void update(double elapsedTime) {
        if (!this.exploding) {
            if (velocityX == 0) {
                posY += velocityY;
            } else {
                posX += velocityX * elapsedTime;
                posY += velocityY * elapsedTime;
            }
            if (this.isOutVertically() || this.isOutHorizontally()) {
                toRemove = true;
            }
        }
        if(this.destroyed) {
            if (!playingExplosion){
                new Thread((new BackgroundMusicPlayer(musicFile))).start();
                playingExplosion = true;
            }
        }
    }

    @Override
    public void draw(Graphics gc) {
        if (!exploding) {
            gc.setColor(new Color(153, 0, 0));
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
    public synchronized void collides(A_InteractableObject a_interactableObject) throws ExecutionException, InterruptedException {
        if (a_interactableObject instanceof Player || a_interactableObject instanceof Asteroid) {
            if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                this.exploding = true;
            }
        }
    }

    @Override
    public int getMaxSize() {
        return ShootStrategy.SHOT_SIZE;
    }

    private boolean isOutVertically() {
        return this.posY < 0 || this.posY > Utilities.HEIGHT;
    }

    private boolean isOutHorizontally() {
        return this.posX < 0 || this.posX > Utilities.WIDTH;
    }

    public boolean isToRemove() {
        return toRemove;
    }
}
