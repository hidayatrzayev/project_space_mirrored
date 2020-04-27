package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicSystem extends JPanel
{
    private GraphicsConfiguration graphicsConf =
            GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration();
    private BufferedImage imageBuffer;
    private Graphics graphics;


    public GraphicSystem()
    {
        this.setSize(1280, 720);
        imageBuffer = graphicsConf.createCompatibleImage(
                this.getWidth(), this.getHeight());
        graphics = imageBuffer.getGraphics();


    }


    public Graphics getG()
    {
        return graphics;
    }

    public void clear()
    {

    }

    public void draw()
    {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0,0, 1280, 720);

    }
    public void redraw()
    {
        this.getGraphics().drawImage(imageBuffer, 0, 0, this);
    }


}
