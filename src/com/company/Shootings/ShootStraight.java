package com.company.Shootings;

import com.company.WorldObjects.A_InteractableObject;
import com.company.WorldObjects.Enemy;
import com.company.WorldObjects.EnemyShot;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.List;

public class ShootStraight implements ShootStrategy {

    @Override
    public void shoot(Enemy enemy, List<A_InteractableObject> shots) throws IOException {
        EnemyShot shot =  new EnemyShot((enemy.getPosX() + enemy.getImg().getWidth() / 2) - this.SHOT_SIZE / 2,
                enemy.getPosY() + enemy.getImg().getHeight() - this.SHOT_SIZE / 2,
                this.SHOT_SIZE, this.SHOT_SIZE, 0, 5);
        shots.add(shot);
    }
}
