package com.company.WorldObjects;

import com.company.Services.ComplicatedMesh;
import com.company.Services.Utilities;
import com.company.UIComponents.BossEnemyHealthIndicator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class BossEnemy extends Enemy {

    private BossEnemyHealthIndicator healthIndicator;

    public BossEnemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed, int health) throws IOException {
        super(posX, posY, sizeX, sizeY, img, speed, health);
        this.explosionAnimations = Utilities.explosionAnimations;
        this.mesh = new ComplicatedMesh(img);
        this.healthIndicator = new BossEnemyHealthIndicator(health, posX, posY, sizeX, sizeY);
    }

    @Override
    public void update(double elapsedTime) {
        super.update(elapsedTime);
        if (posY > Utilities.HEIGHT / 4 || (posY == 0 && direction < 0)) {
            this.direction *= -1;
        }

        this.healthIndicator.update(this.posX, this.posY);
    }

    @Override
    public void draw(Graphics gc) {
        super.draw(gc);
        this.healthIndicator.draw(gc, health);
    }
}
