package com.jdc.pos.service;

import com.jdc.pos.dto.Account;

public class AccountService {

	public Account login(String loginId, String password) {	
		
		Account user = findById(loginId);
		
		
		return null;
	}
	
	private Account findById(String loginId) {
		return null;
	}

}
