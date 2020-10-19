package com.jdc.bot;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class BotController extends Application {

	@FXML
	private ListView<String> output;

	@FXML
	private TextField input;

	private MrBot bot;

	@FXML
	private void initialize() {
		input.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					// get user input
					String message = input.getText();
					// show in output list
					output.getItems().add(String.format("YOU > %s", message));

					// talk to bot
					String result = bot.talk(message);
					output.getItems().add(String.format("BOT > %s", result));
					output.getItems().add("");

					input.clear();
				}
			}
		});
		bot = new MrBot();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("BotController.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
