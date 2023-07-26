/*
 * Project: TowerDefence
 * Created Date: Wednesday, March 15th 2023, 11:44:08 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */

package towerdefence;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Views.MainWindow;

public class App extends Application {

	public void start(Stage stage) throws Exception {
		logger.info("Main window started");
		new MainWindow(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	private final static Logger logger = LogManager.getLogger(App.class);
}
