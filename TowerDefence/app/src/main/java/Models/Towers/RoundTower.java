/*
 * Project: TowerDefence
 * Created Date: Tuesday, March 21st 2023, 9:19:48 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Models.Towers;

public class RoundTower extends AbstractTower {

    @Override
    public void setTargetArea(double x, double y) {
        targetRadius = Math.min(dist(this.x, this.y, x, y), radius * 2.0) + 2 * radius;
        // Особая линейная функция для вычисления интенсивности поражения в зоне действия башни.
        intensive = -(1.0 / 3.0) * targetRadius / radius + 1.5;
    }

    @Override
    public double getAttackPower() {
        return intensive * attackPower;
    }

    @Override
    public boolean ableToAttack(double x, double y) {
        return dist(getX(), getY(), x, y) <= getTargetRadius();
    }
}
