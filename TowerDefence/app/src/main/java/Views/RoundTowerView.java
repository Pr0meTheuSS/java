/*
 * Project: TowerDefence
 * Created Date: Tuesday, April 25th 2023, 7:12:29 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Views;

import Models.Towers.RoundTower;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RoundTowerView  extends AbstractTowerView {

    public RoundTowerView(RoundTower tower) {
        super(tower);
        if (tower.getTargetRadius() != 0.0) {
            shapes.add(0, new Circle(tower.getX(), tower.getY(), tower.getTargetRadius(), Color.color(0.0, 0, 0.5, tower.getIntensive())));
        }
    }
}
