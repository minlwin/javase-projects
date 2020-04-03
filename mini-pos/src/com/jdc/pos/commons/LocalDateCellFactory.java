package com.jdc.pos.commons;

import java.time.LocalDate;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LocalDateCellFactory<S> implements Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>>{
	
	@Override
	public TableCell<S, LocalDate> call(TableColumn<S, LocalDate> column) {
		
		return new TableCell<S, LocalDate>() {
			
			@Override
			protected void updateItem(LocalDate date, boolean empty) {

				if(null == date || empty) {
					setText("");
				} else {
					setText(DateUtils.format(date));
				}
			}
		};
	}

}
