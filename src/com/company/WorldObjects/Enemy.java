package com.company.WorldObjects;

import com.company.Services.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.company.Services.Utilities.getAnimations;

public class Enemy extends A_InteractableObject {

    protected int speed;
    protected int health;
    protected int direction;
    protected int lastAttacker;

    /**
     * This constructor specifies the default health of 1 and is meant to be used to create regular enemies
     */
    public Enemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.speed = speed;
        this.health = 1;
        this.direction = 1;
        this.explosionAnimations = getAnimations(51,51,6,8,ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/explosion.png"))));
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
            posY += (speed * direction);
        }
        if (posY > Utilities.HEIGHT) {
            destroyed = true;
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
     * @return {@code true} if two objects collide, otherwise {@code} false.
     */
    public void collides(A_InteractableObject other) {
        if(this.getBounds().intersects(other.getBounds())) {
            if (other instanceof  PlayerShot && !(other instanceof EnemyShot)) {
                if (!other.isDestroyed() && lastAttacker != other.hashCode()) {
                    this.damage();
                    lastAttacker = other.hashCode();
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
            exploding = true;
        }
    }

    public EnemyShot shoot() throws IOException {
        BufferedImage shot = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/shot.png")));
        return new EnemyShot((posX + img.getWidth() / 2) - 26, posY + img.getHeight() - 40,
                shot.getWidth(), shot.getHeight(),
                ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/shot.png"))));
    }
}