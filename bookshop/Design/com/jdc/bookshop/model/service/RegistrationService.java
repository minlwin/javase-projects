package com.jdc.bookshop.model.service;

import com.jdc.bookshop.model.vo.*;
import com.jdc.bookshop.model.service.impl.RegistrationServiceImpl;

public interface RegistrationService {

	PurchaseResult register(PurchaseInput purchase);

	public static RegistrationService getInstance() {
		return new RegistrationServiceImpl();
	}

}