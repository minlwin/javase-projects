package com.jdc.pos.views;

import com.jdc.pos.dto.SaleDetail;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
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
    private TableView<SaleDetail> table;

}
