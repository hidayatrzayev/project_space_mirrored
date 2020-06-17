package com.company.Systems;

import com.company.Main.Universe;
import com.company.Services.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicSystem extends JPanel /*implements MouseMotionListener*/
{
    private GraphicsConfiguration graphicsConf =
            GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration();
    private BufferedImage imageBuffer;
    private Graphics graphics;

    public int mosX;
    public int mosY;


    public GraphicSystem()
    {
        this.setSize(Utilities.WIDTH,Utilities.HEIGHT);
        imageBuffer = graphicsConf.createCompatibleImage(
                this.getWidth(), this.getHeight());
        graphics = imageBuffer.getGraphics();
        this.setFocusable(true);
        //this.addMouseMotionListener(this);
    }


    public Graphics getG()
    {
        return graphics;
    }

    public void clear()
    {

    }

    public void draw(Universe universe)
    {
        universe.draw(graphics);
    }
    public void redraw()
    {
        this.getGraphics().drawImage(imageBuffer, 0, 0, this);
    }



}
