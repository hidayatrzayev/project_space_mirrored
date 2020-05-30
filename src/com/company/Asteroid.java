package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Asteroid extends A_InteractableObject{

    private int direction;
    private int speed;



    public Asteroid(int direction, int speed, int posX, int posY, int size, BufferedImage img ){
        super(posX, posY, size, img);
        this.direction = direction;
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    @Override
    public void update() {
        if (this.direction == 1 || this.direction == 2){
            this.posX += this.speed;
            this.posY += this.speed;
        }else {
            this.posX -= this.speed;
            this.posY += this.speed;
        }

    }

    @Override
    public void draw(Graphics gc) {
        if(!this.exploding) {
            gc.drawImage(this.img, this.posX, this.posY, null);
        }else{
            if(explosionStep != 100) {
                //gc.drawImage(this.img, this.posX, this.posY, null);
                this.explosionStep++;
            }else{
                this.destroyed = true;
            }
        }
    }

}


