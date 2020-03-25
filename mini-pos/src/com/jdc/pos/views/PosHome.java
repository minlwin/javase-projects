package com.jdc.pos.views;

import com.jdc.pos.dto.TopItem;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private LineChart<String, Integer> chart;

    @FXML
    private Label categories;

    @FXML
    private Label products;

    @FXML
    private Label sales;

}
