package com.jdc.pos.views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.jdc.pos.commons.AutoComplete;
import com.jdc.pos.commons.DateUtils;
import com.jdc.pos.commons.DecimalFormatedCellFactory;
import com.jdc.pos.commons.DecimalFormatedConverter;
import com.jdc.pos.commons.MmkConverter;
import com.jdc.pos.commons.TaxHelper;
import com.jdc.pos.context.PosContext;
import com.jdc.pos.dto.Product;
import com.jdc.pos.dto.Sale;
import com.jdc.pos.dto.SaleDTO;
import com.jdc.pos.dto.SaleDetail;
import com.jdc.pos.service.CategoryRepository;
import com.jdc.pos.service.ProductService;
import com.jdc.pos.service.SaleService;
import com.jdc.pos.service.TaxRepository;
import com.jdc.pos.views.custom.ProductItem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
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
    private TableColumn<SaleDetail, Integer> countColumn;

    @FXML
    private TableColumn<SaleDetail, Integer> priceColumn;

    @FXML
    private TableColumn<SaleDetail, Integer> totalColumn;

    @FXML
    private Label subTotal;

    @FXML
    private Label tax;

    @FXML
    private Label total;
    
    @FXML
    private Label saleDate;
    
    @FXML
    private Label saleTime;
    
    @FXML
    private Label taxRate;

    
    private TaxHelper taxProperty;
        
    private StringProperty categoryProperty = new SimpleStringProperty();
    private SaleService sales;
    private ProductService products;
    
    private SaleDTO saleDto;
    
    private List<SaleDetail> deleteList;
    
    @FXML
    private void initialize() {
    	
    	sales = new SaleService();
    	products = new ProductService();
    	deleteList = new ArrayList<>();
    	
    	AutoComplete.attach(schCategory, CategoryRepository.getRepository()::search, categoryProperty::set);
    	
    	categoryProperty.addListener((a, b, c) -> searchProduct());
    	schProduct.textProperty().addListener((a, b, c) -> searchProduct());
    	schCategory.textProperty().addListener((a,b,c) -> {
    		if(c.isEmpty()) {
    			searchProduct();
    		}
    	});
    	
    	searchProduct();
    	
    	saleDate.setText(DateUtils.format(LocalDate.now()));
    	saleTime.setText(DateUtils.format(LocalTime.now()));
    	
    	int taxRateValue = TaxRepository.getRepository().tax(LocalDate.now());
    	taxRate.setText(String.format("%d %%", taxRateValue));
    	
    	taxProperty = new TaxHelper();
    	taxProperty.taxRate().set(taxRateValue);
    	
    	subTotal.textProperty().bindBidirectional(taxProperty.subTotal(), new MmkConverter());
    	tax.textProperty().bindBidirectional(taxProperty.tax(), new MmkConverter());
    	total.textProperty().bindBidirectional(taxProperty.total(), new MmkConverter());
    	
    	headerTotal.textProperty().bind(total.textProperty());
    	
    	MenuItem delete = new MenuItem("Delete");
    	delete.setOnAction(e -> delete());
    	cart.setContextMenu(new ContextMenu(delete));
    	
    	cart.setOnKeyPressed(e -> {
    		if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {
    			delete();
    		}
    	});
    		
    	countColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DecimalFormatedConverter()));
    	priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DecimalFormatedConverter()));
    	totalColumn.setCellFactory(new DecimalFormatedCellFactory<>());
    	
    	countColumn.setOnEditCommit(event -> {
    		SaleDetail data = event.getRowValue();
    		data.setQuantity(event.getNewValue());
    		calculate();
    	});
    	
    	priceColumn.setOnEditCommit(event -> {
    		SaleDetail data = event.getRowValue();
    		data.setUnitPrice(event.getNewValue());
    		calculate();
    	});
    	
    }

	@FXML
    void clear() {
    	cart.getItems().forEach(item -> {
    		if(item.getId() > 0) {
    			item.setDeleted(true);
    			deleteList.add(item);
    		}
    	});
    	
    	cart.getItems().clear();
    	calculate();
    }

	@FXML
    void paid() {

		try {
			
			if(null == saleDto) {
				saleDto = new SaleDTO();
				Sale sale = saleDto.getSale();
				sale.setSaleDate(LocalDate.now());
				sale.setSaleTime(LocalTime.now());
				sale.setSalePersonId(PosContext.getLoginUser().getLoginId());
				sale.setTaxRate(TaxRepository.getRepository().tax(sale.getSaleDate()));
			}
			
			saleDto.getDetails().clear();
			saleDto.getDetails().addAll(deleteList);
			saleDto.getDetails().addAll(cart.getItems());
			
			// save sales
			sales.save(saleDto);
			
			prepareForNext();

			
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
    	
    	SaleDetail order = cart.getItems().stream().filter(a -> a.getProductId() == p.getId()).findAny().orElse(null);
    	
    	if(null == order) {
    		order = new SaleDetail();
    		order.setProduct(p);
    		cart.getItems().add(order);
    	}
    	
    	order.setQuantity(order.getQuantity() + 1);
    	
    	calculate();
    	
    }
    
    private void prepareForNext() {
    	saleDto = null;
    	saleDate.setText(DateUtils.format(LocalDate.now()));
    	saleTime.setText(DateUtils.format(LocalTime.now()));
    	cart.getItems().clear();
    }
    
    private  void delete() {
    	
    	SaleDetail target = cart.getSelectionModel().getSelectedItem();
    	cart.getItems().remove(target);
    	
    	if(target.getId() > 0) {
    		target.setDeleted(true);
    		deleteList.add(target);
    	}
    }
    
    private void calculate() {

    	int amount = cart.getItems().stream()
    			.mapToInt(a  -> a.getUnitPrice() * a.getQuantity()).sum();
    	
    	taxProperty.subTotal().set(amount);
    	
    	List<SaleDetail> list = new ArrayList<>(cart.getItems());
    	cart.getItems().clear();
    	cart.getItems().addAll(list);
	}

	public static Parent getView(Sale sale) {
		try {
			
			FXMLLoader loader = new FXMLLoader(SaleDetails.class.getResource("SaleDetails.fxml"));
			Parent view = loader.load();
			SaleDetails controller = loader.getController();
			controller.set(sale);
			return view;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void set(Sale sale) {
		
		saleDto = sales.find(sale);
	  	taxRate.setText(String.format("%d %%", sale.getTaxRate()));
    	taxProperty.taxRate().set(sale.getTaxRate());
	    cart.getItems().addAll(saleDto.getDetails());
    	saleDate.setText(DateUtils.format(sale.getSaleDate()));
    	saleTime.setText(DateUtils.format(sale.getSaleTime()));
    	
    	calculate();
	    
	}

}
