package com.company.WorldObjects;

import com.company.Services.ComplicatedMesh;
import com.company.Movements.MoveStrategy;
import com.company.Services.Utilities;
import com.company.Shootings.ShootDeathSpiral;
import com.company.Shootings.ShootStrategy;
import com.company.Systems.BackgroundMusicPlayer;
import com.company.Systems.SessionSystem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Enemy extends A_InteractableObject {

    protected int speed;
    protected int health;
    protected int direction;
    protected double shootingInterval;
    protected double intervalAccumulation = 0.0;
    protected MoveStrategy moveStrategy;
    protected ShootStrategy shootStrategy;
    protected List<A_InteractableObject> lastAttacker;
    protected static final String musicFile = "resources/Audio/ShipExplosion.wav";
    protected boolean playingExplosion = false;

    /**
     * This constructor specifies the default health of 1 and is meant to be used to create regular enemies
     */
    public Enemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.speed = speed;
        this.health = 1;
        this.direction = 1;
        this.explosionAnimations = Utilities.explosionAnimations;
        this.mesh = new ComplicatedMesh(img);
        this.lastAttacker = new ArrayList<>();
    }

    /**
     * This constructor specifies a dynamic health value and is meant to be used to create boss enemies
     */
    public Enemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed, int health) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.speed = speed;
        this.health = health;
        this.direction = 1;
        this.lastAttacker = new ArrayList<>();
    }

    @Override
    public void update(double elapsedTime) {
        if (!exploding && !destroyed) {
            moveStrategy.move(this, speed, elapsedTime);
        }else {
            if (!playingExplosion){
                new Thread((new BackgroundMusicPlayer(musicFile))).start();
                playingExplosion = true;
            }
        }
    }

    @Override
    public void draw(Graphics gc) {
        if (exploding) {
            if(explosionStep < 35) {
                gc.drawImage(img, posX, posY, null);
                gc.drawImage(this.explosionAnimations[explosionStep], this.posX + img.getWidth()/4, this.posY + img.getHeight()/4, null);
                this.explosionStep++;
            }else if(explosionStep < 48) {
                gc.drawImage(this.explosionAnimations[explosionStep], this.posX + img.getWidth()/4, this.posY + img.getHeight()/4, null);
                this.explosionStep++;
            }
            else{
                this.destroyed = true;
            }
        } else {
            gc.drawImage(img, posX, posY, null);
        }
    }

    /**
     * Checks if this enemy collides with any other interactable object.
     *
     * @param other - other object to check the collision against
     * @return {@code true} if two objects collide, otherwise {@code false}.
     */
    @Override
    public synchronized void collides(A_InteractableObject other) throws ExecutionException, InterruptedException {
        if (other instanceof PlayerShot || other instanceof Player) {
            if (this.getBounds().intersects(other.getBounds())) {
                if (!this.lastAttacker.contains(other)) {
                    this.damage();
                    this.lastAttacker.add(other);
                }
            }
        }
    }

    /**
     * Applies damage to the enemy by decrementing its health.
     * If the health comes down to 0 after deduction, the enemy is destroyed.
     */
    public void damage() {
        health--;
        if (health == 0) {
            if (this instanceof Enemy && !(this instanceof BossEnemy)){
                SessionSystem.getInstance().setCurrentScore(100);
            }else {
                SessionSystem.getInstance().setCurrentScore(1000);
            }
            exploding = true;
        }
    }

    public void shoot(List<A_InteractableObject> shots) throws IOException {
        intervalAccumulation -= shootingInterval;
        shootStrategy.shoot(this, shots);
    }

    private void setShootingInterval() {
        if (shootStrategy instanceof ShootDeathSpiral) {
            this.shootingInterval = 0.01;
        } else {
            this.shootingInterval = 1;
        }
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
        this.setShootingInterval();
    }

    public boolean isTimeToShoot(double elapsedTime) {
        intervalAccumulation += elapsedTime;
        return intervalAccumulation >= shootingInterval;
    }

    public void setSpeed(int speed) {
        if (speed > 0) {
            this.speed = speed;
        }
    }
}
