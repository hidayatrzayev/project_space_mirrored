package com.company;

import javax.swing.*;

public class GameFrame extends JFrame
{
    private GraphicSystem panel = null;
    public GameFrame()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280,720);
        panel = new GraphicSystem();
        this.setContentPane(panel);
    }

    public GraphicSystem getPanel() {return panel;}
}


