package com.jdc.bookshop.model.dto;

import java.util.List;
import java.time.LocalDate;

public record ReturnBook(
	LocalDate date,
	List<RentBook> books
) {

	public int total() {
		return books.stream().mapToInt(RentBook::charge).sum();
	}
}