package com.jdc.pos.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private Label message;

    @FXML
    private TextField loginId;

    @FXML
    private PasswordField password;

    @FXML
    void close() {
    	loginId.getScene().getWindow().hide();
    }

    @FXML
    void login() {
    	PosFrame.show();
    	close();
    }

}
