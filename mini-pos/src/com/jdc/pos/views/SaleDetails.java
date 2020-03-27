package com.jdc.pos.views;

import com.jdc.pos.commons.AutoComplete;
import com.jdc.pos.dto.Product;
import com.jdc.pos.dto.SaleDetail;
import com.jdc.pos.service.CategoryRepository;
import com.jdc.pos.service.ProductService;
import com.jdc.pos.service.SaleService;
import com.jdc.pos.views.custom.ProductItem;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

public class SaleDetails {

    @FXML
    private TextField schCategory;

    @FXML
    private TextField schProduct;

    @FXML
    private TilePane container;

    @FXML
    private Label headerTotal;

    @FXML
    private TableView<SaleDetail> cart;

    @FXML
    private Label subTotal;

    @FXML
    private Label tax;

    @FXML
    private Label total;
    
    private DoubleProperty subTotalProperty = new SimpleDoubleProperty();
    
    private StringProperty categoryProperty = new SimpleStringProperty();
    private SaleService sales;
    private ProductService products;
    
    @FXML
    private void initialize() {
    	
    	sales = new SaleService();
    	products = new ProductService();
    	
    	AutoComplete.attach(schCategory, CategoryRepository.getRepository()::search, categoryProperty::set);
    	
    	categoryProperty.addListener((a, b, c) -> searchProduct());
    	schProduct.textProperty().addListener((a, b, c) -> searchProduct());
    	
    	searchProduct();
    }

    @FXML
    void clear() {
    	cart.getItems().clear();
    	calculate();
    }

	@FXML
    void paid() {

		try {
			// save sales
			sales.save(cart.getItems());
			
			clear();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void searchProduct() {
    	
    	try {
    		
    		container.getChildren().clear();
    		
    		products.search(schCategory.getText(), schProduct.getText(), true).stream()
    			.map(p -> new ProductItem(p, this::addToCart))
    			.forEach(container.getChildren()::add);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    private void addToCart(Product p) {
    	
    	SaleDetail order = cart.getItems().stream().filter(a -> a.getId() == p.getId()).findAny().orElse(new SaleDetail());
    	
    	if(order.getId() == 0) {
    		order.setProduct(p);
    		cart.getItems().add(order);
    	}
    	
    	order.setQuantity(order.getQuantity() + 1);
    	
    }
    
    private void calculate() {
		// TODO Auto-generated method stub
		
	}

}
