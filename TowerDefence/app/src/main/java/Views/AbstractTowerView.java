// Copyright 2023 Olimpiev Y. Y.
package Views;

import java.util.ArrayList;
import java.util.List;

import Models.Towers.AbstractTower;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class AbstractTowerView  implements InterfaceEntityView {

    public <T extends AbstractTower> AbstractTowerView(T tower) {
        shapes.add(new Circle(tower.getX(), tower.getY(), tower.getRadius(), Color.GREEN));

        var towerTarget = tower.getCurrentTarget(); if (towerTarget != null) {
            var attackLine = new Line(tower.getX(), tower.getY(), towerTarget.getX(), towerTarget.getY());
            shapes.add(attackLine);
        }
    }

    @Override
    public Iterable<Shape> getShapes() {
        return shapes;
    }    

    protected List<Shape> shapes = new ArrayList<>();
}
