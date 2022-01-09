package com.jdc.bookshop.model.vo;

import java.util.List;
import java.time.LocalDate;

public record PurchaseInput(
	LocalDate date,
	String person,
	int transportation,
	List<PurchaseBookInput> books
) {}