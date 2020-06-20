package com.company.Movements;

import com.company.WorldObjects.A_InteractableObject;

public class MoveSinusoidNarrow implements MoveStrategy {

    private double totalElapsedTime = 0.0;

    @Override
    public void move(A_InteractableObject object, int speed, double elapsedTime) {
        totalElapsedTime += elapsedTime;
        object.setPosY((object.getPosY() + speed));
        int newPosX = Math.round(((float) (object.getPosX() + 100 * Math.cos(totalElapsedTime) * elapsedTime)));
        object.setPosX(newPosX);
    }
}
