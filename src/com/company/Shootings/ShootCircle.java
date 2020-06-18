package com.company.Shootings;

import com.company.WorldObjects.A_InteractableObject;
import com.company.WorldObjects.Enemy;
import com.company.WorldObjects.EnemyShot;

import java.io.IOException;
import java.util.List;

public class ShootCircle implements ShootStrategy {

    @Override
    public void shoot(Enemy enemy, List<A_InteractableObject> shots) throws IOException {
        int shotAmount = 10;
        double theta = 2 * Math.PI / shotAmount;
        for (int i = 0; i < shotAmount; i++) {
            EnemyShot shot = new EnemyShot(enemy.getPosX() + enemy.getSizeX() / 2,
                    enemy.getPosY() + enemy.getSizeY() / 2,
                    this.SHOT_SIZE, this.SHOT_SIZE,
                    180 * Math.cos(theta * i), 180 * Math.sin(theta * i));
            shots.add(shot);
        }
    }
}
