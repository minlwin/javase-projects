package com.jdc.bookshop.model.vo;

public record PurchaseBookInput(
	String title,
	String author,
	String type,
	int purchasePrice,
	int rentCharge,
	int count
) {}