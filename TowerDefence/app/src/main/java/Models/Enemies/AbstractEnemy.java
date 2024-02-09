/*
 * Project: TowerDefence
 * Created Date: Monday, April 3rd 2023, 4:52:41 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * All shall be well and all shall be well and all manner of things shall be well.
 * Nope...we're doomed!
 * -----
 */

package Models.Enemies;

import Models.Attackable;
import Models.Attacker;

public class AbstractEnemy implements Attacker, Attackable {

    @Override
    public double getAttackPower() {
        return attackPower;
    }

    @Override
    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isCollision(double x, double y) {
        return dist(this.x, this.y, x, y) <= radius;
    }

    @Override
    public void causeDamage(double damage) {
        health -= damage;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public boolean isAlive() {
        return health >= 0.0;
    }

    @Override
    public boolean ableToAttack(double x, double y) {
        return dist(this.x, this.y, x, y) <= attackRadius;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void setCurrentTarget(Attackable target) {
        currentTarget = target;
    }

    @Override
    public Attackable getCurrentTarget() {
        if (currentTarget != null) {
            currentTarget = currentTarget.isAlive() ? currentTarget : null;
            return currentTarget;
        }
        
        return null;
    }

    protected double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    protected double x = 0.0;
    protected double y = 0.0;
    protected double speed = 1.0;
    protected double attackPower = 1.0;
    protected double health = 80.0;
    protected double radius = 10;
    protected double attackRadius = 20;
    protected Attackable currentTarget;
}
