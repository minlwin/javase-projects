package com.jdc.bookshop.model.dto;

public record Book(
	String code,
	int dailyCharges,
	PurchaseBook purchase,
	Status status
) {

	public Book(String code, int dailyCharges, PurchaseBook purchase) {
		this(code, dailyCharges, purchase, Status.Available);
	}

	public BookInfo info() {
		return purchase.book();
	}

	public Book changePrice(int price) {
		return new Book(code, price, purchase, status);
	}

	public enum Status {
		Available,
		InHand,
		Lost,
		Damage
	}

}