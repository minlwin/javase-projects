package com.jdc.hellofx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloJavaFX {

    @FXML
    private TextField input;

    @FXML
    private Label output;

    @FXML
    void greet(ActionEvent event) {
    	output.setText(input.getText());
    	input.clear();
    }

}
