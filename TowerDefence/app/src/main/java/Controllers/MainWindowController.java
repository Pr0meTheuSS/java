/*
 * Project: TowerDefence
 * Created Date: Monday, April 17th 2023, 8:27:38 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * -----
 */
package Controllers;

import javafx.application.Platform;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Views.LevelWindow;


public class MainWindowController extends Stage {
    public void handleStartGameButton() {
        logger.info("Level was started");        
        if(!isLevelWindowOpened) {
            var lvlWin = new LevelWindow();
            isLevelWindowOpened = true;
            lvlWin.getStage().setOnCloseRequest(e-> {
                isLevelWindowOpened = false;
            });
        }
    }

    public void handleExitButton() {
        logger.info("Game was finished");
        Platform.exit();
        System.exit(0);
    }

    private boolean isLevelWindowOpened = false;
	private final static Logger logger = LogManager.getLogger(MainWindowController.class);
}
