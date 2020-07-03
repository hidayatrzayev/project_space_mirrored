package com.company.Movements;

import com.company.Services.Utilities;
import com.company.WorldObjects.A_InteractableObject;

public class MoveBossEnemy implements MoveStrategy {

    public static final int MARGIN_SIDE = 10;
    public static final int MARGIN_TOP = 20;

    private int direction = 1;

    @Override
    public void move(A_InteractableObject object, int speed, double elapsedTime) {
        if (object.getPosY() < MARGIN_TOP) {
            object.setPosY(object.getPosY() + speed);
        } else{
            if (object.getPosX() + object.getSizeX() >= Utilities.WIDTH - MARGIN_SIDE ||
                    object.getPosX() <= MARGIN_SIDE) {
                this.direction *= -1;
            }
            object.setPosX(object.getPosX() + speed * direction);
        }
    }
}
