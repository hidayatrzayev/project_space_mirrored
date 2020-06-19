package com.company.WorldObjects;

import com.company.Services.CircleMesh;
import com.company.Services.Direction;
import com.company.Services.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.company.Services.Utilities.getAnimations;



public class Asteroid extends A_InteractableObject{

    private Direction direction;
    private int speed;
    private int animationStep = 0;
    private BufferedImage[] animations;
    private long lastAniationLoop;

    public Asteroid(Direction direction, int speed, int posX, int posY, int sizeX, int sizeY, BufferedImage img ) throws IOException {
        super(posX, posY, sizeX, sizeY, img);
        this.direction = direction;
        this.speed = speed;
        this.animations = getAnimations(50,50,2,10,img);
        this.lastAniationLoop = System.nanoTime();
        this.explosionAnimations = getAnimations(51,51,6,8,ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/explosion.png"))));
        this.mesh = new CircleMesh(3,3,38,38);
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
    public void collides(A_InteractableObject a_interactableObject) {
        if (this.equals(a_interactableObject) || a_interactableObject.isDestroyed()){return;}
        if (Utilities.distance(this.posX + this.sizeX/2, this.posY + this.sizeY/2, a_interactableObject.posX + a_interactableObject.getSizeX()/2 , a_interactableObject.getPosY() + a_interactableObject.getSizeY()/2) < Utilities.hypotenuse(a_interactableObject.getMaxSize(), this.getMaxSize())) {
            if (a_interactableObject instanceof Player || a_interactableObject instanceof PlayerShot) {
                if (this.getBounds().intersects(a_interactableObject.getBounds())) {
                    if (!a_interactableObject.isDestroyed()) {
                        this.exploding = true;
                    }
                }
            }
            if (a_interactableObject instanceof Enemy) {
                if (!a_interactableObject.isDestroyed()) {
                    this.direction = Direction.negateDirection(this.direction);
                }
            }
        }
    }


    @Override
    public int getMaxSize(){
        return 50;
    }


}


