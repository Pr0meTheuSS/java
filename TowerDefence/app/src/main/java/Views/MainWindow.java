/*
 * Project: TowerDefence
 * Created Date: Monday, April 17th 2023, 8:22:22 pm
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

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow {
    public MainWindow(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../MenuWindow.fxml"));
			assert(root != null);
			Scene scene = new Scene(root, 900, 250);
			stage.setResizable(false);

			stage.setTitle("Save IE. Tower Defence");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			Platform.exit();
			System.exit(1);
		}
    }
}
