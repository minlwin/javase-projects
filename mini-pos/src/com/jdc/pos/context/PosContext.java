package com.jdc.pos.context;

import com.jdc.pos.dto.Account;

public class PosContext {

	private static Account loginUser;

	public static void setLoginUser(Account loginUser) {
		PosContext.loginUser = loginUser;
	}

	public static Account getLoginUser() {
		return loginUser;
	}
}
