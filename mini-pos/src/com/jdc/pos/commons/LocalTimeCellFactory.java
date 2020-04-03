package com.jdc.pos.commons;

import java.time.LocalTime;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LocalTimeCellFactory<S> implements Callback<TableColumn<S, LocalTime>, TableCell<S, LocalTime>>{

	@Override
	public TableCell<S, LocalTime> call(TableColumn<S, LocalTime> column) {
		return new TableCell<S, LocalTime>() {
		
			@Override
			protected void updateItem(LocalTime time, boolean empty) {
				
				if(null == time || empty)  {
					setText("");
				} else {
					setText(DateUtils.format(time));
				}
			}
		};
	}

}
