package com.jdc.pos.commons;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DecimalFormatedCellFactory<S> implements Callback<TableColumn<S, Integer>, TableCell<S, Integer>>{

	private static final DecimalFormatedConverter converter = new DecimalFormatedConverter();

	@Override
	public TableCell<S, Integer> call(TableColumn<S, Integer> column) {
		
		return new TableCell<S, Integer>() {			
			@Override
			protected void updateItem(Integer data, boolean empty) {
				if(null == data || empty) {
					setText("");
				} else {
					setText(converter.toString(data));
				}
			}
		};
	}

}
