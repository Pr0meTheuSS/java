/*
 * Project: TowerDefence
 * Created Date: Tuesday, April 18th 2023, 9:24:26 pm
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

import java.util.ArrayList;
import java.util.List;

import Models.Mosque.Mosque;
import javafx.scene.shape.Shape;
import Views.Image;

public class MosqueView implements InterfaceEntityView {

    public MosqueView(Mosque mosqueModel) {
		Image mosqueImage = new Image("/mosque.png");
		mosqueImage.setX(mosqueModel.getX() - mosqueModel.getRadius()  / 2);
		mosqueImage.setY(mosqueModel.getY()- mosqueModel.getRadius()  / 2);
		mosqueImage.setWidth(mosqueModel.getRadius() * 2);
		mosqueImage.setHeight(mosqueModel.getRadius() * 2);
        shapes.add(mosqueImage);
    }

    @Override
    public Iterable<Shape> getShapes() {
        return shapes;
    }
    
    private List<Shape> shapes = new ArrayList<Shape>();
}
