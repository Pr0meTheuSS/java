/*
 * Project: TowerDefence
 * Created Date: Tuesday, April 4th 2023, 8:10:34 pm
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

public interface Movable {
    double getX();
    double getY();
    void moveTo(double x, double y);
    boolean isCollision(double x, double y);
}
