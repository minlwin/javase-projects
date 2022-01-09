package com.jdc.bookshop.model.vo;

import java.util.*;
import com.jdc.bookshop.model.dto.*;

public record PurchaseResult(
	Purchase purchase,
	List<PurchaseBook> books
) {

	public int total() {
		return subTotal() + purchase.transportation();
	}

	public int subTotal() {
		return books.stream().mapToInt(b -> b.total()).sum();
	}

	public int transportation() {
		return purchase.transportation();
	}

}