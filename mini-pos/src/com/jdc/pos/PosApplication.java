package com.jdc.pos;

import com.jdc.pos.views.Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PosApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setScene(new Scene(FXMLLoader.load(Login.class.getResource("Login.fxml"))));
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
