/*
 * Project: TowerDefence
 * Created Date: Tuesday, April 4th 2023, 8:08:03 pm
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

public interface Attacker extends Movable {
    double getAttackPower();
    boolean ableToAttack(double x, double y);
    void setCurrentTarget(Attackable target);
    Attackable getCurrentTarget();
}
