/*
 * Project: TowerDefence
 * Created Date: Monday, April 17th 2023, 8:38:34 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Controllers;

import Models.Level;
import Models.Towers.LongTower;
import Models.Towers.RoundTower;
import Views.AbstractEnemyView;
import Views.LevelWindow;
import Views.MosqueView;
import Views.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelWindowController {

	public void handleRoundTowerButton() {
        logger.info("Player clicked round tower button");
		if (playerState == PlayerState.idle) {
			if (levelModel.buildTower(new RoundTower())) {
				playerState = PlayerState.buildTower;
			}
		}
	}

	public void handleLongTowerButton() {
        logger.info("Player clicked long tower button");
		if (playerState == PlayerState.idle) {
			if (levelModel.buildTower(new LongTower())) {
				playerState = PlayerState.buildTower;
			}
		}
	}	

	public void handleGameOverButton() {
        logger.info("Player clicked continue button");
		gameResultLabel.setText("");
		
		runLevel(levelLoaderController.getCurrentLevel());
		soundController.playMusic("peace");
    }

	public void handleStartLevel() {
        logger.info("Player clicked start level button");
		levelModel.start();
		soundController.playMusic("war");
	}

	public void connectWindowToController(LevelWindow view) {
        levelWindow = view;
		view.setAvailableTowersLabel(availableTowersLabel);
		view.setGameResultLabel(gameResultLabel);
		view.setCanvas(canvas);
		view.setGameOverButton(gameOverButton);

		view.getStage().setOnHiding(e -> {
			logger.info("Level window hidden");
			soundController.stopMusic();
		});
	}

	public int getAvailableTowersCount() {
		return levelModel.getCurrentTowerLimit();
	}

	public void runLevel(int levelId) {
		levelModel = new Level(levelId);
		try {
			availableTowersLabel.setText("Available Towers: " + levelModel.getCurrentTowerLimit());
			currentLevelLabel.setText("Current level: " + levelLoaderController.getCurrentLevel());
			timeLine = new Timeline(new KeyFrame(javafx.util.Duration.millis(33), e -> update()));
			timeLine.setCycleCount(Timeline.INDEFINITE);
			timeLine.play();
			soundController.playMusic("peace");
		} catch(NullPointerException | IllegalArgumentException ex) {
			System.err.println("Catch error in level init.\n" + ex.getMessage());
			throw new RuntimeException(ex);
		}
	}


	public void handleMouseClick(double x, double y) {
		globalX = x;
		globalY = y;
		switch (playerState) {
			case idle:
				break;

			case buildTower:
				if (levelModel.checkAbleToBuildTower(x, y)) {
					levelModel.implaceBuildingTower(globalX, globalY);
					playerState = PlayerState.setTargetAreaForTower;
				}
				break;

			case setTargetAreaForTower:
			levelModel.setTargetAreaForBuildingTower(globalX, globalY);
				playerState = PlayerState.idle;
				break;

			default:
				break;
		}
	}

	public void handleMouseMoved(double x, double y) {
		globalX = x;
		globalY = y;

		switch (playerState) {
			case idle:
				break;

			case buildTower:
				levelModel.implaceBuildingTower(globalX, globalY);
				break;

			case setTargetAreaForTower:
				levelModel.setTargetAreaForBuildingTower(globalX, globalY);
				break;

			default:
				break;
		}
	}

	private void update() {
		levelModel.update();
        levelWindow.update();

		if (levelModel.isGameOver()) {
			timeLine.stop();
            levelWindow.handleGameOver(levelModel.isShadyWon());
			if (levelModel.isShadyWon()) {
				levelLoaderController.incrementLevel();
			}
			soundController.stopMusic();
		} else {          
            // Delegate to draw mosque.  
            var mosqueView = new MosqueView(levelModel.getMosque());
            levelWindow.draw(mosqueView.getShapes());

			// Delegate to draw towers.  
            for (var tower : levelModel.getTowers()) {
				levelWindow.draw(TowerViewCreator.getTowerView(tower).getShapes());
            }

            // Delegate to draw enemies.
            if (levelModel.isGameStart()) {
                for (var enemy : levelModel.getEnemies()) {
                    var enemyView = new AbstractEnemyView(enemy);
                    levelWindow.draw(enemyView.getShapes());
                }
            }
		}
    }

	enum PlayerState {
		idle,
		buildTower,
		setTargetAreaForTower,	
	}

	
	protected LevelWindow levelWindow;
    protected Level levelModel;
    protected Timeline timeLine;
	protected SoundController soundController = new SoundController();
	

	private double globalX = 0;
	private double globalY = 0;
	private PlayerState playerState = PlayerState.idle;
	private LevelLoaderController levelLoaderController = new LevelLoaderController(); 
	private final static Logger logger = LogManager.getLogger(LevelWindow.class);
	@FXML
	private Canvas canvas; 
	@FXML
	private Label availableTowersLabel;
	@FXML
	private Label gameResultLabel;
	@FXML
	private Button gameOverButton;
	@FXML
	private Label currentLevelLabel; 
}

class LevelLoaderController {
	public int getCurrentLevel() {
		return currentLevel;
	}

	public void incrementLevel() {
		currentLevel++;
		currentLevel %= maxLevel;
	}

	private int currentLevel = 0;
	private final int maxLevel = 2;
}
