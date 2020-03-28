package com.jdc.pos.views;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.jdc.pos.commons.AutoComplete;
import com.jdc.pos.dto.Product;
import com.jdc.pos.service.CategoryRepository;
import com.jdc.pos.service.ProductService;
import com.jdc.pos.views.custom.ProductItem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ProductManagement {

	@FXML
	private TextField schCategory;

	@FXML
	private TextField schProduct;

	@FXML
	private TilePane container;

	private ProductService service;
	
	private StringProperty categoryProperty = new SimpleStringProperty();

	@FXML
	private void initialize() {
		
		service = new ProductService();
		AutoComplete.attach(schCategory, CategoryRepository.getRepository()::search, categoryProperty::set);
		
		categoryProperty.addListener((a,b,c) -> search());
		schProduct.textProperty().addListener((a,b,c) -> search());
		schProduct.textProperty().addListener((a,b,c) -> {
			if(c.isEmpty()) {
				search();
			}
		});
		
		search();
	}

	@FXML
	void upload() {
		
		try {
			
			FileChooser fc = new FileChooser();
			fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
			
			fc.setSelectedExtensionFilter(new ExtensionFilter("TSV File", Arrays.asList("*.tsv")));
			File file = fc.showOpenDialog(container.getScene().getWindow());
			
			if(null != file) {
				service.upload(file);
				search();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void search() {
		try {
			container.getChildren().clear();
			List<Product> products = service.search(schCategory.getText(), schProduct.getText());
			products.stream().map(p -> new ProductItem(p, this::changeState)).forEach(container.getChildren()::add);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeState(Product p) {
		service.changeState(p);
		search();
	}

}
