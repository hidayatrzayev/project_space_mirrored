package com.company.JPanel;

import com.company.Services.GameState;
import com.company.Services.Utilities;
import com.company.Systems.SessionSystem;
import com.company.UIComponents.CustomButton;
import com.company.WorldObjects.Star;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel
{
    private Image background;
    private Image title;
    private ArrayList<Star> stars = new ArrayList<>();

    public MenuPanel() throws IOException {
        //BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        //this.setLayout(layout);
        this.setBackground(Color.BLACK);

        GridBagConstraints constr = new GridBagConstraints();
        constr.insets = new Insets(10,10,10,10);
        this.background = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Levels/MenuBackground2.png")))
                .getScaledInstance(Utilities.WIDTH, Utilities.HEIGHT, Image.SCALE_SMOOTH);

        this.title = ImageIO.read((getClass().getClassLoader().getResourceAsStream("Levels/title.png")));
        for(int i = 0; i < 100; i++)
        {
            Star s = new Star(Utilities.random.nextInt(Utilities.WIDTH), Utilities.random.nextInt(Utilities.HEIGHT));
            stars.add(s);
        }

        CustomButton b = new CustomButton(0, "Start New Game");
        CustomButton d = new CustomButton(0, "Continue Game");
        CustomButton c = new CustomButton(0, "Exit");

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SessionSystem.getInstance().startNewGame();
            }
        });

        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               System.exit(0);
            }
        });

        d.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SessionSystem.getInstance().loadProgress();
            }
        });

        this.add(b, constr);
        this.add(d, constr);
        this.add(c, constr);
        this.setAlignmentY(0.5f);
        ActionListener listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                repaint();
            }
        };
        Timer timer = new Timer(30, listener);
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (int i = 0; i < stars.size(); i++)
        {
            Star s = stars.get(i);
            s.posY += 1 * (i > 50 ? 1 : 2);
            if (s.posY >= Utilities.HEIGHT)
            {
                s.setPos(Utilities.random.nextInt(Utilities.WIDTH), 0);
            }

        }

        g.drawImage(background, 0, 0,null);


        for (int i = 0; i < stars.size(); i++)
        {
            Star s = stars.get(i);
            g.setColor((i > 50 ?Color.WHITE : Color.LIGHT_GRAY));
            g.drawRect(s.posX, s.posY, 1, 1);
        }


        g.drawImage(title, (Utilities.WIDTH - title.getWidth(null))/2, (Utilities.HEIGHT - title.getHeight(null))/2 - 200,null);
    }
}
