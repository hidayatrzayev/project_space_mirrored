package com.company.Shootings;

import com.company.WorldObjects.A_InteractableObject;
import com.company.WorldObjects.Enemy;
import com.company.WorldObjects.EnemyShot;

import java.io.IOException;
import java.util.List;

public interface ShootStrategy {

    int SHOT_SIZE = 12;

    void shoot(Enemy enemy, List<A_InteractableObject> shots) throws IOException;
}
