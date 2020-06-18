package com.company.Systems;

import com.company.WorldObjects.A_InteractableObject;
import com.company.WorldObjects.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputSystem
{
    private final AtomicBoolean canShoot = new AtomicBoolean(true);



    public void configureInput(GraphicSystem graphicSystem, Player player, int playerSpeed, ArrayList<A_InteractableObject> shots)
    {


        graphicSystem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                //Check if the pressed key is a control key, if not, set the variable to zero (ignore the key press)
                int key = e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN ||
                        e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
                        || e.getKeyCode() == KeyEvent.VK_W? e.getKeyCode() : 0;

                //Movement
                if(key == KeyEvent.VK_UP) player.setVelY(-playerSpeed);
                if(key == KeyEvent.VK_DOWN) player.setVelY(playerSpeed);
                if(key == KeyEvent.VK_LEFT) player.setVelX(-playerSpeed);
                if(key == KeyEvent.VK_RIGHT) player.setVelX(playerSpeed);


                //Regular shot
                if(key == KeyEvent.VK_W) {
                    //Prevent from shooting multiple times if the key is held down
                    if (true) {
                        if (shots.size() < 20) {
                            try {
                                shots.add(player.shoot());
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }

                }


            }
        });

        //Stops the movement of the player if a key is released
        graphicSystem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN ||
                        e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT
                        || e.getKeyCode() == KeyEvent.VK_W ? e.getKeyCode(): 0;
                if(key == KeyEvent.VK_UP) player.setVelY(0);
                if(key == KeyEvent.VK_DOWN) player.setVelY(0);
                if(key == KeyEvent.VK_RIGHT) player.setVelX(0);
                if(key == KeyEvent.VK_LEFT) player.setVelX(0);

                if(key == KeyEvent.VK_W)
                {
                    canShoot.set(true);
                }
            }
        });

    }
}
