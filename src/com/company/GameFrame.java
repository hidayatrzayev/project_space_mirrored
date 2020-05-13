package com.company;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GameFrame extends JFrame
{
    private GraphicSystem panel = null;
    //TODO: change to private after testing
    public int mosX = 0;
    public GameFrame()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280,720);
        panel = new GraphicSystem();
        this.setContentPane(panel);
    }
    public GraphicSystem getPanel() {return panel;}


}


