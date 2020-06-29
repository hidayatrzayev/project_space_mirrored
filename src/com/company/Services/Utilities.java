package com.company.Services;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Utilities
{



    public static int WIDTH ;
    public static int HEIGHT;
    public static Random random = new Random();
    public static BufferedImage[] explosionAnimations;
    public static BufferedImage[] asteroidMovementAnimations;

    static {
        try {
            explosionAnimations = getAnimations(51, 51, 6, 8,
                    ImageIO.read((
                            Objects.requireNonNull(
                                    Utilities.class.getClassLoader().getResourceAsStream("Actions/explosion.png")
                            )
                    )));

            asteroidMovementAnimations = getAnimations(50, 50, 2, 10,
                    ImageIO.read((
                            Objects.requireNonNull(
                                    Utilities.class.getClassLoader().getResourceAsStream("Asteroids/asteroid3.png")
                            )
                    )));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }


    private static BufferedImage[] getAnimations(int width, int height, int rows, int cols, BufferedImage spritesheet){
        BufferedImage[] sprites = new BufferedImage[rows * cols];
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                sprites[(i * cols) + j] = spritesheet.getSubimage(j * width, i * height, width, height);
            }
        }
        return sprites;
    }

    public static void setWIDTH(int WIDTH) {
        Utilities.WIDTH = WIDTH;
    }

    public static void setHEIGHT(int HEIGHT) {
        Utilities.HEIGHT = HEIGHT;
    }


    public static int hypotenuse(int sizeA, int sizeB) {
        return (int) Math.sqrt(Math.pow(sizeA, 2) + Math.pow(sizeB, 2));
    }

}   
