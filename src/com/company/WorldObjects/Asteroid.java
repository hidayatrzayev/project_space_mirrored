package com.company.WorldObjects;

import com.company.Services.CircleMesh;
import com.company.Services.Direction;
import com.company.Services.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Asteroid extends A_InteractableObject{

    private Direction direction;
    private int speed;
    private int animationStep = 0;
    private BufferedImage[] animations;
    private long lastAniationLoop;
    private List<A_InteractableObject> lastAttacker;

    public Asteroid(Direction direction, int speed, int posX, int posY, int sizeX, int sizeY, BufferedImage img ) {
        super(posX, posY, sizeX, sizeY, img);
        this.direction = direction;
        this.speed = speed;
        this.animations = Utilities.asteroidMovementAnimations;
        this.lastAniationLoop = System.nanoTime();
        this.explosionAnimations = Utilities.explosionAnimations;
        this.mesh = new CircleMesh(3,3,38,38);
        this.lastAttacker = new ArrayList<>();
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    @Override
    public void update(double elapsedTime) {
        if(!this.exploding) {
            if (this.direction == Direction.RIGHT) {
                this.posX += this.speed;
                this.posY += this.speed;
            } else if (this.direction == Direction.LEFT){
                this.posX -= this.speed;
                this.posY += this.speed;
            }else if (this.direction == Direction.DOWN){
                this.posY += this.speed;
            }else if (this.direction == Direction.UP){
                this.posY -= this.speed;
            }
            if ((System.nanoTime() - this.lastAniationLoop) > 80000000) {
                if (animationStep < 19) {
                    animationStep++;
                    this.lastAniationLoop = System.nanoTime();
                } else {
                    animationStep = 0;
                    this.lastAniationLoop = System.nanoTime();
                }
            }
        }

    }

    @Override
    public void draw(Graphics gc) {
        if(!this.exploding) {
            gc.drawImage(this.animations[animationStep], this.posX, this.posY, null);
        }else{
            if(explosionStep != 48) {
                gc.drawImage(this.explosionAnimations[explosionStep], this.posX, this.posY, null);
                this.explosionStep++;
            }else{
                this.destroyed = true;
            }
        }
    }

    @Override
    public synchronized void collides(A_InteractableObject a_interactableObject) throws ExecutionException, InterruptedException {
            if (a_interactableObject instanceof Player || a_interactableObject instanceof PlayerShot) {
                if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                        this.exploding = true;
                }
            }
            if (a_interactableObject instanceof Enemy) {
                if (!lastAttacker.contains(a_interactableObject)){
                    if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                        this.direction = Direction.negateDirection(this.direction);
                        this.lastAttacker.add(a_interactableObject);
                    }
                }
            }
    }


    @Override
    public int getMaxSize(){
        return 50;
    }


}


