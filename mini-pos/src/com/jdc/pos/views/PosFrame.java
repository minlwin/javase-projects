package com.jdc.pos.views;

import com.jdc.pos.commons.ViewLoader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PosFrame implements ViewLoader{
	
	@FXML
	private StackPane content;
	@FXML
	private HBox titleBar;
	
	private static ViewLoader viewLoader;
	
	public static ViewLoader getViewLoader() {
		return viewLoader;
	}
	
	@FXML
	private void initialize() {
		loadView("PosHome");
		viewLoader = this;
	}

	@FXML
	private void handleAction(MouseEvent event) {
		var source = event.getSource();
		
		if(source instanceof VBox) {
			var box = (VBox) source;
			loadView(box.getId());
		}
	}

	private void loadView(String id) {
		
		try {
			
			Parent view = FXMLLoader.load(getClass().getResource(id.concat(".fxml")));
			content.getChildren().clear();
			content.getChildren().add(view);
			
			titleBar.getChildren().stream()
				.filter(node -> node instanceof VBox)
				.map(node -> (VBox)node)
				.forEach(box -> {
					if(box.getId().equals(id)) {
						box.getStyleClass().add("icon-active");
					} else {
						box.getStyleClass().remove("icon-active");
					}
				});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void show() {

		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(PosFrame.class.getResource("PosFrame.fxml"));
			stage.setScene(new Scene(root));
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void loadView(Parent view) {
		content.getChildren().clear();
		content.getChildren().add(view);
	}
}
