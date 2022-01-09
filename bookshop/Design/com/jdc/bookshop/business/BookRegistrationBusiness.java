package com.jdc.bookshop.business;

import java.util.*;
import com.jdc.bookshop.Business;
import com.jdc.bookshop.model.dto.*;
import com.jdc.bookshop.model.vo.*;
import com.jdc.bookshop.model.service.RegistrationService;

public class BookRegistrationBusiness implements Business {

	private RegistrationService service;

	public BookRegistrationBusiness() {
		service = RegistrationService.getInstance();
	}

	public void doAction() {
		// Show Business Title
		showTitle("Book Registration");

		// get User Inputs
		var input = getUserInputs();

		// do registration
		var result = service.register(input);

		// show result
		showResult(result);

	}

	private void showResult(PurchaseResult result) {
		showMessage("Registration is done!");

		// Sub Total
		System.out.printf("Sub Total : %d%n", result.subTotal());
		// Transportation
		System.out.printf("Transport : %d%n", result.transportation());
		// Total
		System.out.printf("Total     : %d%n", result.total());

	}

	private PurchaseInput getUserInputs() {

		// Get Books
		showMessage("Enter Purchase Books");
		var books = getBooks();

		// Purchase Info
		showMessage("Enter Purchase Information");

		// Get Person 
		var person = input().getString("Employee Name");

		// Get Date
		var date = input().getDate("Purchase Date");

		// Get Transportation
		var transport = input().getInt("Transportation");

		// Create PurchaseInput 
		return new PurchaseInput(date, person, transport, books);
	}

	private List<PurchaseBookInput> getBooks() {

		var list = new ArrayList<PurchaseBookInput>();

		do {
			// get book
			list.add(new PurchaseBookInput(
				input().getString("Book Title"),
				input().getString("Author"),
				input().getString("Book Type"),
				input().getInt("Purchase Price"),
				input().getInt("Rent Charges"),
				input().getInt("Quantity")
			));

			// ask more
		} while(askMoreBook());

		return list;
	}

	private boolean askMoreBook() {
		System.out.println();
		System.out.println("Add More Books (Y / Others)");
		return "Y".equalsIgnoreCase(input().getString("Your Answer"));
	}

}