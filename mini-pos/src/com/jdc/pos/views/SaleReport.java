package com.jdc.pos.views;

import java.time.LocalDate;
import java.util.List;

import com.jdc.pos.commons.AutoComplete;
import com.jdc.pos.commons.DecimalFormatedCellFactory;
import com.jdc.pos.commons.LocalDateCellFactory;
import com.jdc.pos.dto.SaleDetail;
import com.jdc.pos.service.CategoryRepository;
import com.jdc.pos.service.SaleService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SaleReport {

    @FXML
    private TextField schCategory;

    @FXML
    private TextField schProduct;

    @FXML
    private DatePicker schFrom;

    @FXML
    private DatePicker schTo;

    @FXML
    private TableView<SaleDetail> table;
    @FXML
    private TableColumn<SaleDetail, LocalDate> colDate;

    @FXML
    private TableColumn<SaleDetail, Integer> colPrice;

    @FXML
    private TableColumn<SaleDetail, Integer> colCount;

    @FXML
    private TableColumn<SaleDetail, Integer> colSub;

    @FXML
    private TableColumn<SaleDetail, Integer> colTax;

    @FXML
    private TableColumn<SaleDetail, Integer> colTotal;
    
    private StringProperty categoryProperty;
    
    private CategoryRepository categories;
    private SaleService sales;
    
    @FXML
    private void initialize() {
    	
    	categoryProperty = new SimpleStringProperty();
    	categories = CategoryRepository.getRepository();
    	sales = new SaleService();
    	
    	AutoComplete.attach(schCategory, categories::search, categoryProperty::set);
    	
    	schCategory.textProperty().addListener((a,b,c) -> {
    		if(c.isEmpty()) {
    			search();
    		}
    	});
    	
    	schFrom.valueProperty().addListener((a,b,c) -> search());
    	schTo.valueProperty().addListener((a,b,c) -> search());
    	categoryProperty.addListener((a,b,c) -> search());
    	schProduct.textProperty().addListener((a,b,c) -> search());
    	
    	colDate.setCellFactory(new LocalDateCellFactory<>());
    	colPrice.setCellFactory(new DecimalFormatedCellFactory<>());
    	colCount.setCellFactory(new DecimalFormatedCellFactory<>());
    	colSub.setCellFactory(new DecimalFormatedCellFactory<>());
    	colTax.setCellFactory(new DecimalFormatedCellFactory<>());
    	colTotal.setCellFactory(new DecimalFormatedCellFactory<>());
   	
    	schFrom.setValue(LocalDate.now().withDayOfMonth(1));
    	
    }
    
    private void search() {
    	table.getItems().clear();
    	List<SaleDetail> list  = sales.searchDetails(schCategory.getText(), schProduct.getText(), schFrom.getValue(), schTo.getValue());   	
    	table.getItems().addAll(list);
    }
    
}
