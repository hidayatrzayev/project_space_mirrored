package com.company.Main;

import com.company.JPanel.MenuPanel;
import com.company.Services.Utilities;
import com.company.Systems.GraphicSystem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame
{
    private GraphicSystem panel = null;
    private MenuPanel menuPanel = null;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GameFrame() throws IOException {


        menuPanel = new MenuPanel();
        panel = new GraphicSystem();

        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Utilities.WIDTH,Utilities.HEIGHT);

        this.setContentPane(menuPanel);
    }
    public GraphicSystem getPanel() {return panel;}

    public void changePanel()
    {
        this.setVisible(false);
        this.setContentPane(panel);
        this.validate();
        this.setVisible(true);

    }

    public void changeToMenu()
    {
        this.setContentPane(menuPanel);
        this.validate();
        this.setVisible(true);
    }




}


