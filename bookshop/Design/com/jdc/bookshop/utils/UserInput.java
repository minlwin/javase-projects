package com.jdc.bookshop.utils;

import java.time.*;
import java.time.format.*;
import java.util.Scanner;

public class UserInput {

	private Scanner scanner;
	private static UserInput instance;

	public static UserInput getInstance() {
		if(null == instance) {
			instance = new UserInput();
		}

		return instance;
	}

	private UserInput () {
		scanner = new Scanner(System.in);
		scanner.useDelimiter("\n");
	}

	public int getInt(String message) {
		showMessage(message);
		return scanner.nextInt();
	}

	public String getString(String message) {
		showMessage(message);
		return scanner.next();
	}

	public LocalDate getDate(String message) {
		var input = getString(message);
		return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}	

	private void showMessage(String message) {
		System.out.printf("%-16s: ", message);
	}
}