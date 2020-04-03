package com.jdc.pos.views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.jdc.pos.commons.AutoComplete;
import com.jdc.pos.commons.DecimalFormatedCellFactory;
import com.jdc.pos.commons.LocalDateCellFactory;
import com.jdc.pos.commons.LocalTimeCellFactory;
import com.jdc.pos.dto.Account;
import com.jdc.pos.dto.Sale;
import com.jdc.pos.service.AccountService;
import com.jdc.pos.service.SaleService;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SaleHistory {

    @FXML
    private TextField schPerson;

    @FXML
    private DatePicker schFrom;

    @FXML
    private DatePicker schTo;

    @FXML
    private TableView<Sale> table;
    @FXML
    private TableColumn<Sale, LocalDate> colDate;

    @FXML
    private TableColumn<Sale, LocalTime> colTime;

    @FXML
    private TableColumn<Sale, Integer> colCount;

    @FXML
    private TableColumn<Sale, Integer> colSub;

    @FXML
    private TableColumn<Sale, Integer> colTax;

    @FXML
    private TableColumn<Sale, Integer> colTotal;    
    
    private AccountService accounts;
    private SaleService sales;
    
    private ObjectProperty<Account> accountProperty;
    
    @FXML
    private void initialize() {
    	
    	accounts = new AccountService();
    	sales = new SaleService();
    	
    	accountProperty = new SimpleObjectProperty<>();
    	
    	AutoComplete.attach(schPerson, accounts::search, accountProperty::set);  	
    	
    	schPerson.textProperty().addListener((a,b,c) -> {
    		if(c.isEmpty()) {
    			search();
    		}
    	});
    	
    	schFrom.valueProperty().addListener((a,b,c) -> search());
    	schTo.valueProperty().addListener((a,b,c) -> search());
    	accountProperty.addListener((a,b,c) -> search());
    	
    	colDate.setCellFactory(new LocalDateCellFactory<>());
    	colTime.setCellFactory(new LocalTimeCellFactory<>());
    	colCount.setCellFactory(new DecimalFormatedCellFactory<>());
    	colSub.setCellFactory(new DecimalFormatedCellFactory<>());
    	colTax.setCellFactory(new DecimalFormatedCellFactory<>());
    	colTotal.setCellFactory(new DecimalFormatedCellFactory<>());

    	schFrom.setValue(LocalDate.now().withDayOfMonth(1));
    }
    
    private void search() {
    	
    	table.getItems().clear();
    	List<Sale> list = sales.search(schPerson.getText(), schFrom.getValue(), schTo.getValue());
    	table.getItems().addAll(list);
    }

}
