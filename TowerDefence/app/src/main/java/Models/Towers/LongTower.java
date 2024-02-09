/*
 * Project: TowerDefence
 * Created Date: Sunday, April 2nd 2023, 10:31:55 pm
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

import Models.Point2D;

public class LongTower extends AbstractTower {
    @Override
    public void setTargetArea(double x, double y) {
        targetRadius = 300;
        Point2D targetMiddleLine;
        
        if (x == getX() && y == getY()) {
            targetMiddleLine = new Point2D(targetRadius,0);
        } else {
            targetMiddleLine = new Point2D(x - getX(), y - getY());
        }

        // First point of the triangle is a tower center.
        targetAreaPolygonVertX[0] = getX();
        targetAreaPolygonVertY[0] = getY();

        // Sub begin from the end.
        targetMiddleLine = targetMiddleLine.normalize().multiply(targetRadius);
        var orth = new Point2D(-targetMiddleLine.getY(), targetMiddleLine.getX());
        orth = orth.normalize();
        orth = orth.multiply(200);

        // Second point of the triangle is a vertex of an isosceles triangle with head in the center of the tower.
        targetAreaPolygonVertX[1] = targetMiddleLine.getX() + getX() + orth.getX();
        targetAreaPolygonVertY[1] = targetMiddleLine.getY() + getY() + orth.getY();

        // Third point of the triangle is a vertex of an isosceles triangle with head in the center of the tower.
        targetAreaPolygonVertX[2] = targetMiddleLine.getX() + getX() - orth.getX();
        targetAreaPolygonVertY[2] = targetMiddleLine.getY() + getY() - orth.getY();
    }

    @Override
    public double getAttackPower() {
        return attackPower;
    }

    @Override
    public boolean ableToAttack(double x, double y) {
        return isPointInTargetAreaPolygon(x, y);
    }

    public Double[] getTargetAreaPolygonVertexes() {
        return new Double[] {
            targetAreaPolygonVertX[0],
            targetAreaPolygonVertX[1],
            targetAreaPolygonVertX[2],
            targetAreaPolygonVertY[0],
            targetAreaPolygonVertY[1],
            targetAreaPolygonVertY[2]
        };
    }

    public int getTargetAreaPolygonDim() {
        return polygonDim;
    }

    private boolean isPointInTargetAreaPolygon(double x, double y) {
        return isPointInTriangle(x, y);
    } 

    private boolean isPointInTriangle(double x, double y) {
        // Read more: https://javarevisited.blogspot.com/2023/01/check-if-given-point-is-inside-triangle.html#ixzz7zzgxji42
        Point2D targetPoint = new Point2D(x, y);
        Point2D A = new Point2D(targetAreaPolygonVertX[0], targetAreaPolygonVertY[0]);
        Point2D B = new Point2D(targetAreaPolygonVertX[1], targetAreaPolygonVertY[1]);
        Point2D C = new Point2D(targetAreaPolygonVertX[2], targetAreaPolygonVertY[2]);

        double areaOfABC = new Triangle(A, B, C).area();
        double areaOfPAB = new Triangle(targetPoint, A, B).area();
        double areaOfPBC = new Triangle(targetPoint, B, C).area();
        double areaOfPCA = new Triangle(targetPoint, C, A).area();

        return (areaOfABC == areaOfPAB + areaOfPBC + areaOfPCA);
    }

    @Override
    public double getIntensive() {
        return 0.5;
    }

    // Long Tower has a triangular target area.
    final private int polygonDim = 3;

    private Double[] targetAreaPolygonVertX = new Double[polygonDim];
    private Double[] targetAreaPolygonVertY = new Double[polygonDim];

}

class Triangle {
    Point2D A;
    Point2D B;
    Point2D C;

    public Triangle(Point2D a, Point2D b, Point2D c) {
        A = a;
        B = b;
        C = c;
    }

    /**
     * Java method to return area of triangle using vertices
     * as per following formula
     * area = (Ax(By -Cy) + Bx(Cy -Ay) + Cx(Ay - By))/2
     * @return
     */
    public double area() {
        double area = (A.getX() * (B.getY() - C.getY()) + B.getX() * (C.getY() - A.getY()) + C.getX() * (A.getY() - B.getY())) / 2.0;
        return Math.abs(area);
    }
}
