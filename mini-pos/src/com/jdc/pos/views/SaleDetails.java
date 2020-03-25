package com.jdc.pos.views;

import com.jdc.pos.dto.SaleDetail;

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
    private TableView<SaleDetail> cartTable;

    @FXML
    private Label subTotal;

    @FXML
    private Label tax;

    @FXML
    private Label total;

    @FXML
    void clear() {

    }

    @FXML
    void paid() {

    }

}
