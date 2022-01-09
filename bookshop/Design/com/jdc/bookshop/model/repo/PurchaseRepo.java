package com.jdc.bookshop.model.repo;

import java.util.*;
import com.jdc.bookshop.model.vo.PurchaseInput;
import com.jdc.bookshop.model.dto.Purchase;
import com.jdc.bookshop.model.repo.generator.*;

public class PurchaseRepo {

	private static PurchaseRepo REPO;
	private Map<Integer, Purchase> states;
	private IdGenerator.Generator idGenerator;

	private PurchaseRepo() {
		states = new HashMap<>();
		idGenerator = IdGenerator.getGenerator();
	}

	public static PurchaseRepo getRepo() {
		if(null == REPO) {
			REPO = new PurchaseRepo();
		}

		return REPO;
	}


	public Purchase create(PurchaseInput data) {
		var purchase = Purchase.createNew(idGenerator.next(), data);
		states.put(purchase.id(), purchase);
		return purchase;
	}

}