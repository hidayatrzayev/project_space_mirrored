package com.company.Main;


import com.company.Services.Utilities;
import com.company.WorldObjects.Star;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;



import java.util.ArrayList;

public class Universe {

    private int posX, posY = 0;
    private int level;
    private String backgroundPath;
    private Image background;
    private int complexity;
    private int numberOfEnemies;
    private String bossEnemy;


    //TODO:change back to private
    public int currentHeight = 0;


    private int scrollSpeed = 1;



    private ArrayList<Star> stars = new ArrayList<>();

    public Universe(int level, String backgroundPath, int complexity, int numberOfEnemies, String bossEnemy) throws IOException{
        this.level = level;
        this.backgroundPath = backgroundPath;
        this.complexity = complexity;
        this.numberOfEnemies = numberOfEnemies;
        this.bossEnemy = bossEnemy;
        this.background = ImageIO.read((getClass().getClassLoader().getResourceAsStream(backgroundPath)))
                .getScaledInstance(Utilities.WIDTH, Utilities.HEIGHT, Image.SCALE_SMOOTH);

        for(int i = 0; i < 500; i++)
        {
           Star s = new Star(Utilities.random.nextInt(Utilities.WIDTH), Utilities.random.nextInt(Utilities.HEIGHT));
           stars.add(s);
        }
    }

    //= ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/level1.jpg"))).getScaledInstance(1280, 720, Image.SCALE_SMOOTH)
    public void update()
    {
        posY = posY;

        for (int i = 0; i < stars.size(); i++)
        {
            Star s = stars.get(i);
            s.posY += scrollSpeed * (i > 200 ? 1 : 2);
            if (s.posY >= Utilities.HEIGHT)
            {
                s.setPos(Utilities.random.nextInt(Utilities.WIDTH), 0);
            }

        }

    }

    public void draw(Graphics gc) {
        currentHeight += 1;
        gc.drawImage(background, posX, posY,null);



        for (int i = 0; i < stars.size(); i++)
        {
            Star s = stars.get(i);
            gc.setColor((i > 100 ?Color.WHITE : Color.LIGHT_GRAY));
            gc.drawRect(s.posX, s.posY, 1, 1);
        }
    }

    public int getComplexity() {
        return complexity;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public Image getBackground() {
        return background;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public void setNumberOfEnemies(int numberOfEnemies) {
        this.numberOfEnemies = numberOfEnemies;
    }

    public String getBossEnemy() {
        return bossEnemy;
    }

    public void setBossEnemy(String bossEnemy) {
        this.bossEnemy = bossEnemy;
    }

    @Override
    public String toString() {
        return "{" +
                "\"level\":\"" + level + '\"' +
                ",\"posX\":\"" + posX + '\"' +
                ",\"posY\":\"" + posY + '\"' +
                ",\"backgroundPath\":\"" + backgroundPath + '\"' +
                ",\"complexity\":\"" + complexity + '\"' +
                ",\"numberOfEnemies\":\"" + numberOfEnemies + '\"' +
                ",\"bossEnemy\":\"" + bossEnemy + '\"' +
                '}';
    }
}

