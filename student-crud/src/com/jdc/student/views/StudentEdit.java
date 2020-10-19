package com.jdc.student.views;

import java.io.IOException;

import com.jdc.student.model.Student;
import com.jdc.student.model.Student.Gender;
import com.jdc.student.model.StudentAppException;
import com.jdc.student.model.StudentModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentEdit {

    @FXML
    private Label message;
    
    @FXML
    private Label title;

    @FXML
    private TextField name;

    @FXML
    private ComboBox<Gender> gender;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;
    
    private StudentModel model;
    
    private SaveListener saveListener;
    private Student student;
    
	@FXML
    private void initialize() {
    	gender.getItems().addAll(Gender.values());
    	model = StudentModel.getModel();
    }

    @FXML
    void save(ActionEvent event) {
    	
    	try {
        	// set with input data
        	student.setName(name.getText());
        	student.setDob(dob.getValue());
        	student.setEmail(email.getText());
        	student.setGender(gender.getValue());
        	student.setPhone(phone.getText());
        	
        	// save to Student Model
        	model.save(student);
        	
        	// reload list view
        	saveListener.reload();
        	
        	// close window
        	name.getScene().getWindow().hide();
		} catch (StudentAppException e) {
			message.setText(e.getMessage());
		}
    }
    
    private void  init(SaveListener listener, Student student) {
    	this.saveListener = listener;
    	this.student = student == null ? new Student() : student;
    	title.setText(this.student.getId() == 0 ? "Add New Student" : "Edit Student");
    
    	name.setText(this.student.getName());
    	dob.setValue(this.student.getDob());
    	gender.setValue(this.student.getGender());
    	phone.setText(this.student.getPhone());
    	email.setText(this.student.getEmail());
    }

	public static void showDialog(SaveListener listener, Student student) {
		
		try {
			// create stage
			Stage stage = new Stage();
			
			// load edit view
			FXMLLoader loader = new FXMLLoader(StudentEdit.class.getResource("StudentEdit.fxml"));
			Parent root = loader.load();
			
			StudentEdit edit = loader.getController();
			edit.init(listener, student);
			
			// set scene
			stage.setScene(new Scene(root));
			
			stage.initModality(Modality.APPLICATION_MODAL);
						
			// show stage
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static interface SaveListener {
		void reload();
	}

}
