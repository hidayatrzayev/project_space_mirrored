package com.company.Main;

import com.company.Services.Utilities;
import com.company.Systems.GraphicSystem;

import javax.swing.*;

public class GameFrame extends JFrame
{
    private GraphicSystem panel = null;
    //TODO: change to private after testing
    public int mosX = 0;
    public GameFrame()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Utilities.WIDTH,Utilities.HEIGHT+39);
        panel = new GraphicSystem();
        this.setContentPane(panel);
    }
    public GraphicSystem getPanel() {return panel;}


}


