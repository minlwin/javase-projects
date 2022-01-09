package com.jdc.bookshop.model.repo;

import java.util.*;
import com.jdc.bookshop.model.dto.PurchaseBook;

public class PurchaseBookRepo {

	private static PurchaseBookRepo REPO;
	private Set<PurchaseBook> states;

	private PurchaseBookRepo() {
		states = new HashSet<>();
	}

	public static PurchaseBookRepo getRepo() {
		if(null == REPO) {
			REPO = new PurchaseBookRepo();
		}

		return REPO;
	}

	public void create(PurchaseBook data) {
		states.add(data);
	}

}