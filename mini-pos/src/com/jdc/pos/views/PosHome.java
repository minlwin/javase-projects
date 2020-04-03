package com.jdc.pos.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jdc.pos.commons.AutoComplete;
import com.jdc.pos.commons.DateUtils;
import com.jdc.pos.commons.DecimalFormatedCellFactory;
import com.jdc.pos.commons.StringUtils;
import com.jdc.pos.dto.Summary;
import com.jdc.pos.dto.TopItem;
import com.jdc.pos.service.CategoryRepository;
import com.jdc.pos.service.SummaryService;
import com.jdc.pos.service.TaxRepository;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PosHome {

    @FXML
    private TextField schCategory;

    @FXML
    private DatePicker schFrom;

    @FXML
    private DatePicker schTo;

    @FXML
    private TableView<TopItem> topItems;
    @FXML
    private TableColumn<TopItem, Integer> colValue;

    @FXML
    private LineChart<String, Integer> chart;

    @FXML
    private Label categories;

    @FXML
    private Label products;

    @FXML
    private Label refDate;

    @FXML
    private Label taxRate;

    @FXML
    private Label sales;
    
    private SummaryService service;
    
    private StringProperty categoryProperty = new SimpleStringProperty();
    
    
    @FXML
    private void initialize() {
    	
    	service = new SummaryService();
    	
    	colValue.setCellFactory(new DecimalFormatedCellFactory<>());
    	
    	AutoComplete.attach(schCategory, CategoryRepository.getRepository()::search, categoryProperty::set);
    	
    	categoryProperty.addListener((a,b,c) -> loadData());
    	
    	schCategory.textProperty().addListener((a,b,c) -> {
    		if(c.isEmpty()) {
    			loadData();
    		}
    	});
    	
    	schFrom.valueProperty().addListener((a,b,c) -> loadData());
    	schTo.valueProperty().addListener((a,b,c) -> loadData());
    	loadData();
    	
    	refDate.setText(DateUtils.format(LocalDate.now()));
    	int tax = TaxRepository.getRepository().tax(LocalDate.now());
    	
    	taxRate.setText(String.format("%d %%", tax));
    }


	private void loadData() {

		// Top Items
		TopItemsLoader topLoader = new TopItemsLoader();
		topLoader.setOnSucceeded(event -> {			
			topItems.getItems().clear();
			topItems.getItems().addAll(topLoader.getValue());
		});
		topLoader.start();
		
		// Chart Data
		ChartDataLoader chartLoader = new ChartDataLoader();
		chartLoader.setOnSucceeded(event -> {
			chart.getData().clear();
			chart.getData().addAll(chartLoader.getValue());
		});
		chartLoader.start();
		
		// Summary Data
		SummaryLoader summaryLoader = new SummaryLoader();
		summaryLoader.setOnSucceeded(event -> {
			Summary data = summaryLoader.getValue();
			categories.setText(StringUtils.kilo(data.getCategory()));
			products.setText(StringUtils.kilo(data.getProduct()));
			sales.setText(StringUtils.kilo(data.getSale()));
		});
	}
	
	private class TopItemsLoader extends Service<List<TopItem>> {

		@Override
		protected Task<List<TopItem>> createTask() {
			return new Task<List<TopItem>>() {

				@Override
				protected List<TopItem> call() throws Exception {
					return service.findTopItems(schCategory.getText(), schFrom.getValue(), schTo.getValue());
				}
			};
		}	
	}
	
	private class ChartDataLoader extends Service<List<Series<String, Integer>>> {

		@Override
		protected Task<List<Series<String, Integer>>> createTask() {
			
			return new Task<List<Series<String,Integer>>>() {

				@Override
				protected List<Series<String, Integer>> call() throws Exception {
					
					List<Series<String, Integer>> list = new ArrayList<>();
					Map<String, Map<String, Integer>> result = service.findChartData(schCategory.getText(), schFrom.getValue(), schTo.getValue());
					
					for(String key : result.keySet()) {
						
						Series<String, Integer> series = new Series<>();
						series.setName(key);
						
						result.get(key).entrySet().stream()
							.map(a -> new Data<String, Integer>(a.getKey(), a.getValue()))
							.forEach(series.getData()::add);
						
						list.add(series);
					}
					
					return list;
				}
			};
		}
	}
	
	private class SummaryLoader extends Service<Summary> {

		@Override
		protected Task<Summary> createTask() {

			return new Task<Summary>() {
				
				@Override
				protected Summary call() throws Exception {
					return service.findSummary(schCategory.getText(), schFrom.getValue(), schTo.getValue());
				}
			};
		}
		
	}

}
