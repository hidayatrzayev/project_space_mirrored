package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Asteroid extends A_InteractableObject{

    private int direction;
    private int speed;
    private int animationStep = 0;
    private BufferedImage[] animations;
    private long lastAniationLoop;



    public Asteroid(int direction, int speed, int posX, int posY, int sizeX, int sizeY, BufferedImage img ){
        super(posX, posY, sizeX, sizeY, img);
        this.direction = direction;
        this.speed = speed;
        this.animations = this.getAnimations();
        this.lastAniationLoop = System.nanoTime();
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
        if((System.nanoTime() - this.lastAniationLoop ) > 80000000) {
            if (animationStep < 19) {
                animationStep++;
                this.lastAniationLoop = System.nanoTime();
            } else {
                animationStep = 0;
                this.lastAniationLoop = System.nanoTime();
            }
        }

    }

    @Override
    public void draw(Graphics gc) {
        if(!this.exploding) {
            gc.drawImage(this.animations[animationStep], this.posX, this.posY, null);
        }else{
            if(explosionStep != 100) {
                //gc.drawImage(this.img, this.posX, this.posY, null);
                this.explosionStep++;
            }else{
                this.destroyed = true;
            }
        }
    }

    @Override
    public void collide(A_InteractableObject a_interactableObject) {
        if(this.getBounds().intersects(a_interactableObject.getBounds())) {
            if (a_interactableObject instanceof Player) {
                System.out.println("Bah");
            }
        }
    }


    private BufferedImage[] getAnimations(){

        final int width = 50;
        final int height = 50;
        final int rows = 2;
        final int cols = 10;
        BufferedImage[] sprites = new BufferedImage[rows * cols];
        for (int j = 0; j < cols; j++)
        {
            for (int i = 0; i < rows; i++)
            {
                sprites[(i * cols) + j] = img.getSubimage(
                        j * width,
                        i * height,
                        width,
                        height
                );
            }
        }




        return sprites;
    }



}


