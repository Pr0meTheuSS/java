package Views;

import java.util.ArrayList;

import Models.Enemies.AbstractEnemy;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class AbstractEnemyView implements InterfaceEntityView {

    public AbstractEnemyView(AbstractEnemy enemyModel) {
        shapes.add(new Circle(enemyModel.getX(), enemyModel.getY(), enemyModel.getRadius(), Color.RED));
    }

    @Override
    public Iterable<Shape> getShapes() {
        return shapes;
    }
    
    protected ArrayList<Shape> shapes = new ArrayList<>();
}
