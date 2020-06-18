package com.company.Shootings;

import com.company.WorldObjects.A_InteractableObject;
import com.company.WorldObjects.Enemy;
import com.company.WorldObjects.EnemyShot;

import java.io.IOException;
import java.util.List;

public class ShootDeathSpiral implements ShootStrategy {

    private double alpha = 0.0;

    @Override
    public void shoot(Enemy enemy, List<A_InteractableObject> shots) throws IOException {
        alpha += 0.1;
        EnemyShot shot = new EnemyShot(enemy.getPosX() + enemy.getSizeX() / 2,
                enemy.getPosY() + enemy.getSizeY(),
                this.SHOT_SIZE, this.SHOT_SIZE,
                180 * Math.cos(alpha), 180 * Math.sin(alpha));
        shots.add(shot);
    }
}
