package com.jdc.bookshop.model.service.impl;

import java.util.*;
import com.jdc.bookshop.model.repo.*;
import com.jdc.bookshop.model.dto.*;
import com.jdc.bookshop.model.vo.*;
import com.jdc.bookshop.model.service.RegistrationService;

public class RegistrationServiceImpl implements RegistrationService {

	public PurchaseResult register(PurchaseInput input) {
		// Create Purchase
		var purchase = PurchaseRepo.getRepo().create(input);

		var books = new ArrayList<PurchaseBook>();

		// loop books
		for(var purchaceBookInput : input.books()) {
			// Get or Create Book Info
			var info = BookInfoRepo.getRepo().getOrCreate(purchaceBookInput);

			// Create Purchase Book
			var purchaseBook = PurchaseBook.getInstance(info, purchase, purchaceBookInput);
			PurchaseBookRepo.getRepo().create(purchaseBook);
			books.add(purchaseBook);

			// Create Books
			BookRepo.getRepo().create(purchaseBook, purchaseBook.count(), purchaceBookInput.rentCharge());

		}

		return new PurchaseResult(purchase, books);
	}

}