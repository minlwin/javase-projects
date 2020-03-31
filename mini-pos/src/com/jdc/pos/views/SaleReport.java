package com.jdc.pos.views;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
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
    private TableView<SaleDetails> table;

}
