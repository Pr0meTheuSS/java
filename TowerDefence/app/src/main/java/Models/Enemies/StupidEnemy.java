/*
 * Project: TowerDefence
 * Created Date: Monday, April 3rd 2023, 4:55:41 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Models.Enemies;

import java.util.Random;
import Models.Point2D;

public class StupidEnemy extends AbstractEnemy {

    public StupidEnemy(double x, double y) {
        Random rand = new Random();
        speed = rand.nextDouble(2.0, 5.0);
        this.x = x;
        this.y = y;
    }

    @Override
    public void moveTo(double targetX, double targetY) {
        Point2D targetVector = new Point2D(targetX - x, targetY - y);

        targetVector = targetVector.normalize();
        targetVector = targetVector.multiply(speed);
        x += targetVector.getX();
        y += targetVector.getY();
    }
}
