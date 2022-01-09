package com.jdc.bookshop.model.dto;

public record Member(
	int id,
	String name,
	String phone,
	String email,
	String address
	) {

}