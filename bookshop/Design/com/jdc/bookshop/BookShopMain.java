package com.jdc.bookshop;

import com.jdc.bookshop.business.*;
import com.jdc.bookshop.utils.UserInput;

public class BookShopMain {

	public static void main(String[] args) {
		new BookShopMain().start();		
	}

	public void start() {

		// Show App Title
		showHeader("Book Shop");

		do {

			// show menu
			int menuId = showAndAskMenu();

			// select business
			Business business = switch(menuId) {
				case 1 -> new RentBookBusiness();
				case 2 -> new ReturnBookBusiness();
				case 3 -> new BookRegistrationBusiness();
				default -> new SearchRentInfoBusiness();
			};

			// do business action
			business.doAction();

			// ask to exits
		} while(askToContinue());

		System.out.println();

		showHeader("Thank You");

	}

	private void showHeader(String message) {
		System.out.println("==============================");
		System.out.println("=======   %s   ========".formatted(message));
		System.out.println("==============================");
		System.out.println();
	}

	private int showAndAskMenu() {
		System.out.println("Please select business.");
		System.out.println("1. Rent Boooks");
		System.out.println("2. Return Boooks");
		System.out.println("3. Book Registration");
		System.out.println("4. Search History");
		System.out.println();
		return UserInput.getInstance().getInt("Business ID");
	}

	private boolean askToContinue() {
		System.out.println();
		System.out.println("Do you want to coninue? (Y / Others)");
		return "Y".equalsIgnoreCase(UserInput.getInstance().getString("Your Answer"));
	}

}