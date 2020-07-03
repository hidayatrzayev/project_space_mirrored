package com.company.WorldObjects;

import com.company.Services.ComplicatedMesh;
import com.company.Services.Utilities;
import com.company.Shootings.ShootStrategy;
import com.company.UIComponents.BossEnemyHealthIndicator;

import java.awt.*;
import java.awt.image.BufferedImage;


public class BossEnemy extends Enemy {

    private BossEnemyHealthIndicator healthIndicator;
    private BossEnemyShield shield;

    public BossEnemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed, int health) {
        super(posX, posY, sizeX, sizeY, img, speed, health);
        this.explosionAnimations = Utilities.explosionAnimations;
        this.mesh = new ComplicatedMesh(img);
        this.healthIndicator = new BossEnemyHealthIndicator(health, posX, posY, sizeX, sizeY);
        this.shield = new BossEnemyShield(this.posX, this.posY, this.sizeX, this.sizeY);
    }

    @Override
    public void update(double elapsedTime) {
        super.update(elapsedTime);
        this.healthIndicator.update(this.posX, this.posY);
        if (this.posY < 0) {
            this.shield.update(this.posX, this.posY);
        }
    }

    @Override
    public void draw(Graphics gc) {
        super.draw(gc);
        this.healthIndicator.draw(gc, health);
        if (this.posY < 0) {
            this.shield.draw(gc);
        }
    }

    public ShootStrategy getShootStrategy() {
        return this.shootStrategy;
    }

    public boolean isShieldToRemove() {
        return this.posY >= 0;
    }

    public BossEnemyShield getShield() {
        return this.shield;
    }
}
