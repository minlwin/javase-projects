package com.jdc.bookshop.model.dto;

import com.jdc.bookshop.model.vo.PurchaseBookInput;

public record PurchaseBook(
	BookInfo book,
	Purchase purchase,
	int unitPrice,
	int count
) {
	public int total() {
		return unitPrice * count;
	}

	public static PurchaseBook getInstance(BookInfo book, Purchase purchase, PurchaseBookInput input) {
		return new PurchaseBook(book, purchase, input.purchasePrice(), input.count());
	}
}