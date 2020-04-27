package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GameWorld
{
    BufferedImage img = null;

    private GraphicSystem graphicSystem;

    public void setup() throws IOException
    {
        img = ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/player_ship.png")));

    }

    public void run()
    {
        while(true)
        {
            graphicSystem.draw();
            //graphicSystem.getG().setColor(Color.RED);
            graphicSystem.getG().drawImage(img, 0, 0, null);
            graphicSystem.redraw();

        }
    }


    public void setGraphicSystem(GraphicSystem p) { graphicSystem = p; }
}
