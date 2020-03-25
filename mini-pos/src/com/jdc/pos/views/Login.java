package com.jdc.pos.views;

import com.jdc.pos.context.PosContext;
import com.jdc.pos.context.PosException;
import com.jdc.pos.dto.Account;
import com.jdc.pos.service.AccountService;

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
    
    private AccountService service;
    
    @FXML
    private void initialize() {
    	service = new AccountService();
    }

    @FXML
    void close() {
    	loginId.getScene().getWindow().hide();
    }

    @FXML
    void login() {
    	
    	try {
    		Account loginUser = service.login(loginId.getText(), password.getText());
    		PosContext.setLoginUser(loginUser);
        	PosFrame.show();
        	close();
		} catch (PosException e) {
			message.setText(e.getMessage());
		}
    }

}
