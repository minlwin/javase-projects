package com.jdc.pos.views;

import java.util.List;

import com.jdc.pos.commons.Validations;
import com.jdc.pos.context.PosException;
import com.jdc.pos.dto.TaxInfo;
import com.jdc.pos.service.TaxRepository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaxConfig {

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField taxRate;

    @FXML
    private TableView<TaxInfo> table;

    @FXML
    private Label message;
    
    private Runnable reloader;
    
    @FXML
    void add() {
    	
    	try {
    		
    		if(null == startDate.getValue()) {
    			throw new PosException("Please select Start Date.");
    		}
    		
    		if(taxRate.getText().isEmpty()) {
    			throw new PosException("Please enter Tax Rate.");
    		}
    		
    		Validations.numberField(taxRate.getText(), "Tax Rate");
    		
    		TaxInfo tax = table.getItems().stream().filter(a -> a.getStartDate().equals(startDate.getValue())).findAny().orElse(null);
    		
    		if(null == tax) {
    			tax = new TaxInfo();
    			tax.setStartDate(startDate.getValue());
    			table.getItems().add(tax);
    		}
    		
    		tax.setPercent(Integer.parseInt(taxRate.getText()));
			
		} catch (Exception e) {
			e.printStackTrace();
			message.setText(e.getMessage());
		}
    	
    }

    @FXML
    void save() {
    	
    	try {
    		
    		TaxRepository.getRepository().save(table.getItems());
    		reloader.run();
    		message.getScene().getWindow().hide();
			
		} catch (Exception e) {
			e.printStackTrace();
			message.setText(e.getMessage());
		}

    }

	public static void loadView(Runnable reloader) {
		try {
			FXMLLoader loader = new FXMLLoader(TaxConfig.class.getResource("TaxConfig.fxml"));
			Parent root = loader.load();
			
			TaxConfig controller = loader.getController();
			controller.reloader = reloader;
			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void initialize() {
		List<TaxInfo> list = TaxRepository.getRepository().getAll();
		table.getItems().addAll(list);
	}

}
