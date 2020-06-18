package com.company.Movements;

import com.company.Main.GameWorld;
import com.company.WorldObjects.A_InteractableObject;

public class MoveSinusoidWide implements MoveStrategy {

    private double totalElapsedTime = 0.0;

    @Override
    public void move(A_InteractableObject object, int speed, double elapsedTime) {
        totalElapsedTime += elapsedTime;
        object.setPosY((object.getPosY() + speed));
        double sinusoid = object.getPosX() + 200
                * Math.cos(totalElapsedTime)
                * elapsedTime;
        int newPosX = Math.round((float) sinusoid);
        object.setPosX(newPosX);
    }
}
