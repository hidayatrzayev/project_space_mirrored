package com.company.Movements;

import com.company.Main.GameWorld;
import com.company.WorldObjects.A_InteractableObject;

public class MoveCircular implements MoveStrategy {

    private double alpha = Math.PI / 2;

    @Override
    public void move(A_InteractableObject object, int speed, double elapsedTime) {
        int newPosX = Math.round(((float) (object.getPosX() + Math.cos(alpha) * speed * elapsedTime)));
        int newPosY = Math.round(((float) (object.getPosY() + Math.sin(alpha) * speed * elapsedTime)));
        object.setPosX(newPosX);
        object.setPosY(newPosY + 1);
        alpha += elapsedTime * (speed / 100.0);
    }
}
