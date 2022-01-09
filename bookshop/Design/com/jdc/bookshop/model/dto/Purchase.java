package com.jdc.bookshop.model.dto;

import java.util.*;
import java.time.LocalDate;
import com.jdc.bookshop.model.vo.*;

public record Purchase(
	int id,
	LocalDate date,
	String person,
	int transportation
) {

	public static Purchase createNew(int id, PurchaseInput input) {
		return new Purchase(id, input.date(), input.person(), input.transportation());
	}
}