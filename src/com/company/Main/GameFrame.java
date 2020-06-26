package com.company.Main;

import com.company.JPanel.MenuPanel;
import com.company.Services.Utilities;
import com.company.Systems.GraphicSystem;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame
{
    private GraphicSystem panel = null;
    private MenuPanel menuPanel = null;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GameFrame()
    {

        menuPanel = new MenuPanel();
        panel = new GraphicSystem();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Utilities.WIDTH,Utilities.HEIGHT);

        this.setContentPane(menuPanel);
    }
    public GraphicSystem getPanel() {return panel;}

    public void changePanel()
    {
        //this.setVisible(false);
//        this.removeAll();
        this.setContentPane(panel);
        this.validate();
        this.setVisible(true);

    }




}


