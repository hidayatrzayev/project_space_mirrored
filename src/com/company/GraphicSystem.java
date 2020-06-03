package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
        this.setSize(1280, 720);
        imageBuffer = graphicsConf.createCompatibleImage(
                this.getWidth(), this.getHeight());
        graphics = imageBuffer.getGraphics();
        //this.addMouseMotionListener(this);
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
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0, 1280, 720);

    }
    public void redraw()
    {
        this.getGraphics().drawImage(imageBuffer, 0, 0, this);
    }
/*

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mosX = e.getX();
        mosY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mosX = e.getX();
        mosY = e.getY();
    }
*/


}
