package com.jdc.bookshop.model.dto;

import java.time.LocalDate;

public record RentHistory(
	LocalDate date,
	Member member
) {

}