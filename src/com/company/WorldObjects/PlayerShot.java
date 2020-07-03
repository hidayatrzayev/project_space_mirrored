package com.company.WorldObjects;

import com.company.Services.CircleMesh;
import com.company.Services.Utilities;
import com.company.Systems.BackgroundMusicPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


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
    private static final String musicFile = "resources/Audio/ShotExplosion.wav";
    private boolean playingExplosion = false;


    public PlayerShot(int posX, int posY, int sizeX, int sizeY, BufferedImage img) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.explosionAnimations = Utilities.explosionAnimations;
        this.mesh = new CircleMesh(10,10,25,25);
    }

    @Override
    public void update(double elapsedTime)
    {
        if (!this.exploding) {
            if (animationStep >= ANIMATION_STEPS) {
                animationStep = 0;
            } else {
                animationStep++;
            }

            //animationStep = animationStep >= ANIMATION_STEPS ? 0 : animationStep++;
            posY -= speed;
        }else {
            if (!playingExplosion){
            new Thread((new BackgroundMusicPlayer(musicFile))).start();
            playingExplosion = true;
            }
        }
    }

    @Override
    public void draw(Graphics gc)
    {
        if (!this.exploding) {
            gc.drawImage(img.getSubimage(0 + (ANIMATION_W * (animationStep / 4)), 0, 52, 52), posX, posY, null);
        }else {
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
            if (a_interactableObject instanceof Enemy || a_interactableObject instanceof Asteroid) {
                if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                        this.exploding = true;
                }
            } else if (a_interactableObject instanceof BossEnemyShield) {
                if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                    this.exploding = true;
                    this.destroyed = true;
                }
            }
    }


    public boolean isExploding(){
        return exploding;
    }

}
