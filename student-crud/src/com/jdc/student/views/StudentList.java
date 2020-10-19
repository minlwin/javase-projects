package com.jdc.student.views;

import java.util.List;

import com.jdc.student.model.Student;
import com.jdc.student.model.StudentModel;
import com.jdc.student.views.StudentEdit.SaveListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class StudentList implements SaveListener{

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student, String> colId;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, String> colGender;

    @FXML
    private TableColumn<Student, String> colDob;

    @FXML
    private TableColumn<Student, String> colPhone;

    @FXML
    private TableColumn<Student, String> colEmail;
    
    private StudentModel model;

    @FXML
    private void initialize() {
    	gender.getItems().addAll("All", "Male", "Female");
    	model = StudentModel.getModel();
    	
    	colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    	colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    	colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    	colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
    	colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    	colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    	
    	// create context menu
    	// edit
    	MenuItem edit = new MenuItem("Edit");
    	edit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				// get selected data
				Student student = table.getSelectionModel().getSelectedItem();
				
				// show edit view with data
				StudentEdit.showDialog(StudentList.this, student);
			}
		});
    	// delete
    	MenuItem delete = new MenuItem("Delete");
    	delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// get selected data
				Student student = table.getSelectionModel().getSelectedItem();
				model.delete(student);
				search(null);
			}
		});
    	
    	// attach to table view
    	table.setContextMenu(new ContextMenu(edit, delete));
    	
    	search(null);
    }

    @FXML
    void addNew(MouseEvent event) {
		StudentEdit.showDialog(this, null);
    }

    @FXML
    void search(ActionEvent event) {
    	table.getItems().clear();
    	List<Student> list = model.search(gender.getValue(), name.getText(), phone.getText());
    	table.getItems().addAll(list);
    }

	@Override
	public void reload() {
		search(null);
	}

}
