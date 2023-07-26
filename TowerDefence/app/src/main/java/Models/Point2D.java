/*
 * Project: TowerDefence
 * Created Date: Saturday, April 15th 2023, 11:36:48 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Models;

public class Point2D {
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    } 

    public double getY() {
        return y;
    } 

    public Point2D multiply(double scl) {
        x *= scl;
        y *= scl;
        return new Point2D(x, y);
    }

    public Point2D normalize() {
        return new Point2D(x / getLength(), y / getLength());
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public Point2D add(Point2D point) {
        x += point.getX();
        y += point.getY();
        return new Point2D(x, y);
    }

    private double x = 0.0;
    private double y = 0.0;
}
