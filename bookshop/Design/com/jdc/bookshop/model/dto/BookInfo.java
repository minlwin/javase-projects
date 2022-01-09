package com.jdc.bookshop.model.dto;

import com.jdc.bookshop.model.vo.*;

public record BookInfo(
	int id,
	String type,
	String title,
	String author
) {

	public static BookInfo createNew(int id, PurchaseBookInput input) {
		return new BookInfo(id, input.type(), input.title(), input.author());
	}

	public boolean isSame(PurchaseBookInput input) {
		return type().equals(input.type()) 
			&& title().equals(input.title()) 
			&& author().equals(input.author());
	}

}