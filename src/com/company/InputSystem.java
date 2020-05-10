package com.company;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class InputSystem extends JPanel implements MouseMotionListener
{
    int mosX = 0;
    public InputSystem()
    {
        this.setSize(1280, 720);
        this.addMouseMotionListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mosX = e.getX();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mosX = e.getX();
    }
}
