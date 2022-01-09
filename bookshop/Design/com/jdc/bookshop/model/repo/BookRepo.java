package com.jdc.bookshop.model.repo;

import java.util.*;
import com.jdc.bookshop.model.dto.*;
import com.jdc.bookshop.model.repo.generator.*;

public class BookRepo {

	private static BookRepo REPO;

	private IdGenerator.CodeGenerator codeGen;
	private Map<String, Book> states;

	private BookRepo() {
		codeGen = IdGenerator.getBookCodeGenerator();
		states = new HashMap<>();
	}

	public static BookRepo getRepo() {
		if(null == REPO) {
			REPO = new BookRepo();
		}

		return REPO;
	}


	public void create(PurchaseBook purchase, int count, int dailyCharge) {

		// Need to Update Daily Charges for old book
		states.keySet().stream()
			.filter(code -> code.startsWith("%4d".formatted(purchase.book().id())))
			.forEach(code -> {
				var target = states.get(code);
				states.put(code, target.changePrice(dailyCharge));
			});

		for(int i = 0; i < count; i ++) {
			var code = codeGen.next(purchase.book().id());
			var book = new Book(code, dailyCharge, purchase);
			states.put(code, book);
		}

	}

}