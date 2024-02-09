/*
 * Project: TowerDefence
 * Created Date: Monday, April 3rd 2023, 11:08:49 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Models.Mosque;

import Models.Attackable;
import Models.Point2D;

public class Mosque implements Attackable {

    public void setMosque(double x, double y, double radius) {
        center = new Point2D(x, y);
        moveTo(x, y);
        this.radius = radius;
    }

    public double getX() {
        return center.getX();
    }

    public double getY() {
        return center.getY();
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void causeDamage(double damage) {
        health -= damage;
    }

    public boolean isAlive() {
        return health > 0.0;
    }

    private Point2D center = new Point2D(0, 0);
    private double radius = 0.0;
    private double health = 50.0;
    
    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void moveTo(double x, double y) {
        center = new Point2D(x, y);
    }

    @Override
    public boolean isCollision(double x, double y) {
        return (Math.abs(x - getX()) <= radius) && (Math.abs(y - getY()) <= radius);
    }
}
