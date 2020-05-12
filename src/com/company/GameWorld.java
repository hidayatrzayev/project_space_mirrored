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
    private InputSystem inputSystem;

    private Player player;

    public void setup() throws IOException
    {
        img = ImageIO.read((getClass().getClassLoader().getResourceAsStream("resources/player_ship.png")));
        player = new Player(100,480,0, img);
        inputSystem = new InputSystem();


    }

    public void run()
    {
        while(true)
        {
            graphicSystem.draw();
            player.setPosX(graphicSystem.mosX);
            player.draw(graphicSystem.getG());
            graphicSystem.getG().setColor(Color.RED);
            graphicSystem.getG().drawString((Integer.toString(graphicSystem.mosX)),  400, 400);
            graphicSystem.redraw();

        }
    }


    public void setGraphicSystem(GraphicSystem p) { graphicSystem = p; }
}
