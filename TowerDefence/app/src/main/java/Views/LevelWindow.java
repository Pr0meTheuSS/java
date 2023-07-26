/*
 * Project: TowerDefence
 * Created Date: Monday, April 17th 2023, 8:38:01 pm
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

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage; 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Controllers.LevelWindowController;

public class LevelWindow {

	public LevelWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../LevelWindow.fxml"));
			Parent root = fxmlLoader.load();

			controller = fxmlLoader.getController();	
			
			Scene scene = new Scene(root, 900, 250);
			stage = new Stage();

			controller.connectWindowToController(this);

			graphicsContext = canvas.getGraphicsContext2D();
			canvas.widthProperty().bind(stage.widthProperty());
			canvas.heightProperty().bind(stage.heightProperty());

			stage.setTitle("Save IE. Tower Defence");
			stage.setScene(scene);
			stage.setFullScreen(true);
			stage.show();

			canvas.setOnMouseClicked(e -> {
				controller.handleMouseClick(e.getX(), e.getY());
			});
			canvas.setOnMouseMoved(e -> {
				controller.handleMouseMoved(e.getX(), e.getY());
			});

			controller.runLevel(0);
			logger.info("Level run");
		} 
		catch (IOException e) {
			logger.error("Error in .fxml loading");
			System.err.println("Catch: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void setGameOverButton(Button btn) {
		gameOverButton = btn;
	}

	public Stage getStage() {
		return stage;
	}
	
	public void update() {
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		availableTowersLabel.setText("Available Towers: " + String.valueOf(controller.getAvailableTowersCount()));
	}

	public void draw(Iterable<? extends Shape> shapes) {
		for (var shape : shapes) {
			drawShapeOnCanvas(shape, graphicsContext);
		}
	}

	public void handleGameOver(boolean isShadyWin) {
		String gameResult = "Game over. Shady is " + (isShadyWin ? "WIN" : "LOSE");
		logger.info(gameResult);
		gameResultLabel.setText(gameResult);
		gameOverButton.setText(isShadyWin ? "Next level" : "Restart");
	}

	public void setGameResultLabel(Label lbl) {
		gameResultLabel = lbl;
	}

	public void setAvailableTowersLabel(Label lbl) {
		availableTowersLabel = lbl;
	}

	public void setCanvas(Canvas cvs) {
		canvas = cvs;
	}

	private void drawShapeOnCanvas(Shape shape, GraphicsContext gc) {
		var oldPaintColor = gc.getFill();
		CanvasDrawer.draw(shape, gc);
		gc.setFill(oldPaintColor);
	}

	private Button gameOverButton;
	private Stage stage;
	private Label gameResultLabel;
	private Canvas canvas; 
	private GraphicsContext graphicsContext;
	private Label availableTowersLabel;
	private LevelWindowController controller;
	private final static Logger logger = LogManager.getLogger(LevelWindow.class);
}	


class CanvasDrawer {
	public static void draw(Shape shape, GraphicsContext graphicsContext) {
		if (shape instanceof Rectangle) {
			draw((Rectangle)shape, graphicsContext);
		} else if (shape instanceof Circle) {
			draw((Circle)shape, graphicsContext);
		} else if (shape instanceof Line) {
			draw((Line)shape, graphicsContext);
		} else if (shape instanceof Polygon) {
			draw((Polygon)shape, graphicsContext);
		} else if (shape instanceof Image) {
			draw((Image)shape, graphicsContext);
		} else {
			throw new RuntimeException("Unknown shape to draw.");
		}	
	}

	public static void draw(Circle circle, GraphicsContext graphicsContext) {
		graphicsContext.setFill(circle.getFill());
		graphicsContext.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), 2.0 *  circle.getRadius(), 2.0 *  circle.getRadius());
	}

	public static void draw(Rectangle rect, GraphicsContext graphicsContext) {
		graphicsContext.setFill(rect.getFill());
		graphicsContext.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		graphicsContext.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

	public static void draw(Line line, GraphicsContext graphicsContext) {
		graphicsContext.setFill(line.getFill());
		graphicsContext.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	}

	public static void draw(Image img, GraphicsContext graphicsContext) {
		graphicsContext.drawImage(img.getImage(), img.getX(), img.getY(), img.getWidth(), img.getHeight());
	}

	public static void draw(Polygon polygon, GraphicsContext graphicsContext) {
		graphicsContext.setFill(polygon.getFill());			

		graphicsContext.fillPolygon(
			new double[] { polygon.getPoints().get(0), polygon.getPoints().get(1), polygon.getPoints().get(2)},
			new double[] { polygon.getPoints().get(3), polygon.getPoints().get(4), polygon.getPoints().get(5)},
			3
			);
		}
}

