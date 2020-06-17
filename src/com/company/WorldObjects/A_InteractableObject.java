package com.company.WorldObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class A_InteractableObject
{
    protected int posX, posY, sizeX, sizeY;
    protected boolean exploding, destroyed;
    protected BufferedImage img;
    protected int explosionStep = 0;
    protected BufferedImage[] explosionAnimations;


    public A_InteractableObject(int posX, int posY, int sizeX, int sizeY, BufferedImage img)
    {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.img = img;
    }

    public abstract void update(double elapsedTime);
    public abstract void draw(Graphics gc);
    public abstract void collides(A_InteractableObject a_interactableObject);
    public Rectangle getBounds()
    {
        return new Rectangle(posX, posY, sizeX - 30, sizeY - 30);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int getExplosionStep() {
        return explosionStep;
    }

    public void setExplosionStep(int explosionStep) {
        this.explosionStep = explosionStep;
    }
}
