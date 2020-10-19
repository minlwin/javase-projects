package com.jdc.student;

import com.jdc.student.model.StudentModel;
import com.jdc.student.views.StudentList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentApp extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(StudentList.class.getResource("StudentList.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() throws Exception {
		// load data from file
		StudentModel.getModel().loadData();
		super.init();
	}
	
	@Override
	public void stop() throws Exception {
		// save to file
		StudentModel.getModel().saveData();
		super.stop();
	}

}
