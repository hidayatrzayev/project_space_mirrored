package com.company.UIComponents;

import com.company.Services.Utilities;
import com.company.Systems.SessionSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomButton extends JComponent implements MouseListener
{
    private Dimension size = new Dimension(Utilities.WIDTH/10, Utilities.HEIGHT/12);
    private int posY;
    private String text;

    private boolean mouseEntered = false;
    private boolean mousePressed = false;

    public CustomButton(int posY, String text)
    {
        super();

        this.enableInputMethods(true);
        this.addMouseListener(this);

        setSize(size.width, size.height);
        setFocusable(true);

        this.posY = posY;
        this.text = text;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 3, 3);
        if(mouseEntered)
        {
            g.setColor(Color.YELLOW);
        }
        else
        {
            g.setColor(Color.LIGHT_GRAY);
        }

        g.drawRoundRect(2, 2, getWidth() - 6, getHeight() - 6, 3, 3);
        g.drawRoundRect(1, 1, getWidth() - 4, getHeight() - 4, 3, 3);
        g.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 3, 3);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.BOLD, Utilities.HEIGHT/50));
        g.drawString(text, 20, 50); //TODO: make dynamic, so text is always in the middle
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(getWidth(), getHeight());
    }

    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
    public Dimension getMaximumSize()
    {
        return getPreferredSize();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        mouseEntered = true;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        mouseEntered = false;
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }
}
