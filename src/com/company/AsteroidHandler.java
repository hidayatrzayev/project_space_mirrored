package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AsteroidHandler {

    private int complexity;
    List<BufferedImage> presets = new ArrayList<BufferedImage>(Arrays.asList(ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid1.png"))),
            ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid2.png"))),
            ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid3.png"))),
            ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid4.png"))),
            ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid5.png")))));
    private List<Asteroid> asteroids = new ArrayList<>();


    public AsteroidHandler(int complexity) throws IOException {
        this.complexity = complexity;
    }

    public void spawnAsteroid() {
        int speed =  this.complexity * 10;
        Random rand = new Random();
        int preset = rand.nextInt(5);
        BufferedImage image = presets.get(preset);
        int coin = rand.nextInt(4);
        int direction = coin;
        int size = 0;
        int posX = 0;
        int posY = 0;
        if (coin == 1){
            posY = rand.nextInt(720);
        }else if(coin == 2){
            posX = rand.nextInt(640);
        }else if(coin == 3){
            posX = rand.nextInt(640)+640;
         }else if (coin == 4){
            posX = 1280;
            posY = rand.nextInt(720);
        }

        asteroids.add(new Asteroid(direction, speed, posX, posY, size, image));
    }

    public void updateAll(){
        asteroids.forEach(Asteroid::update);
        for(int asteroid = 0; asteroid < asteroids.size(); asteroid++){
            if(asteroids.get(asteroid).posX < 0 | asteroids.get(asteroid).posX > 1280 | asteroids.get(asteroid).posY > 780 ){
                asteroids.remove(asteroid);
            }
        }
    }

    public void drawAll(Graphics gc){
        asteroids.forEach(asteroid -> asteroid.draw(gc));
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public List<BufferedImage> getPresets() {
        return presets;
    }

    public void setPresets(List<BufferedImage> presets) {
        this.presets = presets;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(List<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }
}
