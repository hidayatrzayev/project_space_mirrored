package com.company.Handlers;

import com.company.Services.Direction;
import com.company.Services.Utilities;
import com.company.WorldObjects.A_InteractableObject;
import com.company.WorldObjects.Asteroid;

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
//    List<BufferedImage> presets = new ArrayList<BufferedImage>(Arrays.asList(ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid1.png"))),
//            ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid2.png"))),
//            ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid3.png"))),
//            ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid4.png"))),
//            ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/asteroid5.png")))));

    List<BufferedImage> presets = new ArrayList<BufferedImage>();
    private List<A_InteractableObject> asteroids = new ArrayList<>();
    private long lastSpawn;


    public AsteroidHandler(int complexity) throws IOException {
        this.complexity = complexity;
        this.lastSpawn = System.nanoTime();
        String curDir = System.getProperty("user.dir");
        System.out.println(curDir);
        this.presets = Arrays.asList(ImageIO.read((getClass().getClassLoader().getResourceAsStream("Asteroids/asteroid3.png"))));
    }

    private void spawnAsteroid() throws IOException {
        if((System.nanoTime() - this.lastSpawn ) > (10000000000L - this.complexity*1000000000L)) {
            int speed = this.complexity * 3;
            Random rand = new Random(this.lastSpawn);
            int preset = rand.nextInt(5);
            BufferedImage image = presets.get(0); //TODO
            int coin = rand.nextInt(3)+1;
            Direction direction = Direction.RIGHT;
            if (coin == 3 || coin == 4){direction = Direction.LEFT;}
            int posX = 0;
            int posY = 0;
            if (coin == 1) {
                posY = rand.nextInt(Utilities.HEIGHT);
            } else if (coin == 2) {
                posX = rand.nextInt(Utilities.WIDTH /2);
            } else if (coin == 3) {
                posX = rand.nextInt(Utilities.WIDTH /2) + Utilities.WIDTH;
            } else if (coin == 4) {
                posX = Utilities.WIDTH;
                posY = rand.nextInt(Utilities.HEIGHT);
            }

            asteroids.add(new Asteroid(direction, speed, posX, posY, 50, 50, image));
            this.lastSpawn = System.nanoTime();
        }
    }

    public void updateAll(double elapsedTime) throws IOException {
        asteroids.forEach((asteroid) -> asteroid.update(elapsedTime));
        for(int asteroid = 0; asteroid < asteroids.size(); asteroid++){
            if(asteroids.get(asteroid).getPosX() < 0 || asteroids.get(asteroid).getPosX() > Utilities.WIDTH | asteroids.get(asteroid).getPosY() > Utilities.HEIGHT || asteroids.get(asteroid).isDestroyed()){
                asteroids.remove(asteroid);
            }
        }
        this.spawnAsteroid();
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

    public List<A_InteractableObject> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(List<A_InteractableObject> asteroids) {
        this.asteroids = asteroids;
    }
}
