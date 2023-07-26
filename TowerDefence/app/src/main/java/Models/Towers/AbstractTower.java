// Copyright 2023 Olimpiev Y. Y.
package Models.Towers;

import Models.Attackable;
import Models.Attacker;

public class AbstractTower implements Attackable, Attacker {

    @Override
    public double getAttackPower() {
        return attackPower;
    }

    @Override
    public boolean ableToAttack(double x, double y) {
        return false;
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
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
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

    public double getIntensive() {
        return intensive;
    }

    public void setTargetArea(double x, double y) {
        targetRadius = dist(this.x, this.y, x, y);
        intensive = 1.0;
    }

    protected double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public double getRadius() {
        return radius;
    }

    public double getTargetRadius() {
        return targetRadius;
    }

    @Override
    public void setCurrentTarget(Attackable target) {
        currentTarget = target;
    }

    @Override
    public Attackable getCurrentTarget() {
        if (currentTarget != null) {
            currentTarget = currentTarget.isAlive() ? currentTarget : null;
        }
        return currentTarget;
    }

    protected double x = 0.0;
    protected double y = 0.0;
    protected double radius = 50.0;
    protected double targetRadius = 0.0;
    
    protected double health = 10.0;
    protected double attackPower = 10.0;
    protected double intensive = 0.0;
    protected Attackable currentTarget;
}
