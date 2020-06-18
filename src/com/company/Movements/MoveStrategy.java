package com.company.Movements;

import com.company.WorldObjects.A_InteractableObject;

public interface MoveStrategy {

    void move(A_InteractableObject object, int speed, double elapsedTime);
}
