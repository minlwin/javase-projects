package com.jdc.bookshop.model.dto;

public record RentBook(
	int useDays,
	int dailyCharge,
	RentHistory rent,
	Book book
) {

	public int charge() {
		return dailyCharge * useDays();
	}

	public int useDays() {
		// TODO implement here
		return 0;
	}

}