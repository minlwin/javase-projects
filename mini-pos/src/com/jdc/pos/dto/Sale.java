package com.jdc.pos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Sale {

	private int id;
	private LocalDate saleDate;
	private LocalTime saleTime;
	private String salePersonId;
	private int taxRate;
	private String remark;

	private String salePerson;
	private int quantity;
	private int subTotal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public LocalTime getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(LocalTime saleTime) {
		this.saleTime = saleTime;
	}

	public String getSalePersonId() {
		return salePersonId;
	}

	public void setSalePersonId(String salePersonId) {
		this.salePersonId = salePersonId;
	}

	public String getSalePerson() {
		return salePerson;
	}

	public void setSalePerson(String salePerson) {
		this.salePerson = salePerson;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	public int getTax() {
		return subTotal / 100 * taxRate;
	}
	
	public int getTotal() {
		return subTotal  + getTax();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}

}
