package com.company.Movements;

import com.company.WorldObjects.A_InteractableObject;

public class MoveStraightNormal implements MoveStrategy {

    @Override
    public void move(A_InteractableObject object, int speed, double elapsedTime) {
        object.setPosY(object.getPosY() + speed);
    }
}
