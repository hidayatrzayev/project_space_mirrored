package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy extends A_InteractableObject {

    protected int speed;
    protected int health;
    protected int direction;

    /**
     * This constructor specifies the default health of 1 and is meant to be used to create regular enemies
     */
    public Enemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed) {
        super(posX, posY, sizeX, sizeY, img);
        this.speed = speed;
        this.health = 1;
        this.direction = 1;
    }

    /**
     * This constructor specifies a dynamic health value and is meant to be used to create boss enemies
     */
    public Enemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed, int health) {
        super(posX, posY, sizeX, sizeY, img);
        this.speed = speed;
        this.health = health;
        this.direction = 1;
    }

    @Override
    public void update() {
        if (!exploding && !destroyed) {
            posY += (speed * direction);
        }
        if (posY > 720) {
            destroyed = true;
        }
    }

    @Override
    public void draw(Graphics gc) {
        if (exploding) {
            // TODO explosion animation
            destroyed = true;
        } else {
            gc.drawImage(img, posX, posY, null);
        }
    }

    /**
     * Checks if this enemy collides with any other interactable object.
     *
     * @param other - other object to check the collision against
     * @return {@code true} if two objects collide, otherwise {@code} false.
     */
    public boolean collides(A_InteractableObject other) {
        if (!(other instanceof PlayerShot)) {
            return false;
        }

        return this.getBounds().intersects(other.getBounds());
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

    public EnemyShot shoot() throws IOException {
        return new EnemyShot((posX + img.getWidth() / 2) - 26, posY + img.getHeight() - 40,
                img.getWidth(), img.getHeight(),
                ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/shot.png"))));
    }
}
