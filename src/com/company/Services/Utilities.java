package com.company.Services;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Utilities
{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 1000;
    public static Random random = new Random();

    public static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }


    public static BufferedImage[] getAnimations(int width, int height, int rows, int cols, BufferedImage spritesheet){
        BufferedImage[] sprites = new BufferedImage[rows * cols];
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                sprites[(i * cols) + j] = spritesheet.getSubimage(j * width, i * height, width, height);
            }
        }
        return sprites;
    }

}
