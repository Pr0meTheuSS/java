/*
 * Project: TowerDefence
 * Created Date: Friday, June 9th 2023, 10:12:49 pm
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

import javafx.scene.shape.Shape;

class Image extends Shape {
	public Image(String srcFilename) {
		image = new javafx.scene.image.Image("file://" + System.getProperty("user.dir") + srcFilename);
	}

	public javafx.scene.image.Image getImage() {
		return image;
	}


	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public double getWidth() {
		return width;
	}


	public double getHeight() {
		return height;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setX(double x) {
		this.x = x;
	}

	private double x = 0;
	private double y = 0;
	private double width = 0;
	private double height = 0;
	private javafx.scene.image.Image image;
}




