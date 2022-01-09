package com.jdc.bookshop.model.repo;

import java.util.*;
import com.jdc.bookshop.model.dto.BookInfo;
import com.jdc.bookshop.model.vo.PurchaseBookInput;

import com.jdc.bookshop.model.repo.generator.*;


public class BookInfoRepo {

	private static BookInfoRepo REPO;
	private IdGenerator.Generator idGenerator;

	private Map<Integer, BookInfo> states;

	private BookInfoRepo() {
		idGenerator = IdGenerator.getGenerator();
		states = new HashMap<>();
	}

	public static BookInfoRepo getRepo() {
		if(REPO == null) {
			REPO = new BookInfoRepo();
		}

		return REPO;
	}

	public BookInfo getOrCreate(PurchaseBookInput input) {
		// Search Book Info from it's state
		var info = findOne(input);

		if(null == info) {
			// create book info via inputs
			// Generate Id
			info = BookInfo.createNew(idGenerator.next(), input);

			// Store In States
			states.put(info.id(), info);
		}

		return info;
	}

	private BookInfo findOne(PurchaseBookInput input) {
		return states.values().stream()
			.filter(data -> data.isSame(input))
			.findFirst().orElse(null);
	}

}