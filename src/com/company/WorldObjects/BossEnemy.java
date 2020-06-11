package com.company.WorldObjects;

import com.company.Services.Utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.company.Services.Utilities.getAnimations;

public class BossEnemy extends Enemy {


    public BossEnemy(int posX, int posY, int sizeX, int sizeY, BufferedImage img, int speed, int health) throws IOException {
        super(posX, posY, sizeX, sizeY, img, speed, health);
        this.explosionAnimations = getAnimations(51,51,6,8,ImageIO.read((getClass().getClassLoader().getResourceAsStream("Actions/explosion.png"))));
    }

    @Override
    public void update() {
        super.update();
        if (posY > Utilities.HEIGHT / 4 || (posY == 0 && direction < 0)) {
            this.direction *= -1;
        }
    }

    @Override
    public void collides(A_InteractableObject other) {
        super.collides(other);
    }
}
