package com.company.WorldObjects;

import com.company.Services.ComplicatedMesh;
import com.company.Services.Utilities;
import com.company.Main.GameWorld;
import com.company.Movements.MoveStrategy;
import com.company.Shootings.ShootStrategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static com.company.Services.Utilities.getAnimations;

public class Enemy extends A_InteractableObject {

    protected int speed;
    protected int health;
    protected int direction;
    protected double shootingInterval = 1;
    protected double intervalAccumulation = 0.0;
    protected MoveStrategy moveStrategy;
    protected ShootStrategy shootStrategy;

    /**
     * This constructor specifies the default health of 1 and is meant to be used to create regular enemies
     */
    public Enemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.speed = speed;
        this.health = 1;
        this.direction = 1;
        this.explosionAnimations = getAnimations(51,51,6,8,ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/explosion.png"))));
        this.mesh = new ComplicatedMesh(img);
    }

    /**
     * This constructor specifies a dynamic health value and is meant to be used to create boss enemies
     */
    public Enemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed, int health) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.speed = speed;
        this.health = health;
        this.direction = 1;
    }

    @Override
    public void update(double elapsedTime) {
        if (!exploding && !destroyed) {
            moveStrategy.move(this, speed, elapsedTime);
//            posY += (speed * direction);
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
    public void collides(A_InteractableObject other) {
        if(this.getBounds().intersects(other.getBounds())) {
            if (other instanceof  PlayerShot && !(other instanceof EnemyShot)) {
                this.damage();
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
            exploding = true;
        }
    }

    public void shoot(List<A_InteractableObject> shots, double elapsedTime) throws IOException {
        intervalAccumulation += elapsedTime;
        if (intervalAccumulation >= shootingInterval) {
            intervalAccumulation -= shootingInterval;
            shootStrategy.shoot(this, shots);
        }
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public double getShootingInterval() {
        return shootingInterval;
    }

//    public boolean isTimeToShoot() {
//        intervalAccumulation += GameWorld.getElapsedTime();
//        return intervalAccumulation >= shootingInterval;
//    }

    public void setSpeed(int speed) {
        if (speed > 0) {
            this.speed = speed;
        }
    }
}
