package com.company.JPanel;

import com.company.Services.GameState;
import com.company.Systems.SessionSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel
{
    public MenuPanel()
    {
        this.setBackground(Color.BLACK);
        this.add(new JLabel("Menu"));
        JButton b = new JButton("Click Here");
        this.add(b);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionSystem.getInstance().startNewGame();
            }
        });
    }


}
