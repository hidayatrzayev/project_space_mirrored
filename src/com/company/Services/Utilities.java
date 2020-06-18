package com.company.Services;

import java.awt.image.BufferedImage;

public class Utilities
{

    public static int WIDTH ;
    public static int HEIGHT;

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

    public static void setWIDTH(int WIDTH) {
        Utilities.WIDTH = WIDTH;
    }

    public static void setHEIGHT(int HEIGHT) {
        Utilities.HEIGHT = HEIGHT;
    }
}
