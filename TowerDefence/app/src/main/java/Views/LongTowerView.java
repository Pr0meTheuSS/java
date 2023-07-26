/*
 * Project: TowerDefence
 * Created Date: Tuesday, April 25th 2023, 6:23:42 pm
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

import Models.Towers.LongTower;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class LongTowerView extends AbstractTowerView {
    public LongTowerView(LongTower tower) {
        super(tower);
        
        if (tower.getTargetRadius() != 0.0) {
            Polygon triangle = new Polygon();

            triangle.getPoints().addAll(
                tower.getTargetAreaPolygonVertexes()
            );

            triangle.setFill(Color.color(0, 1.0, 0, tower.getIntensive()));
            shapes.add(1, triangle);
        }
    }
}
