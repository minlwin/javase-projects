package com.jdc.bookshop;

import java.util.*;
import com.jdc.bookshop.utils.UserInput;

public interface Business {

	void doAction();

	public default void showTitle(String title) {

		System.out.println("--------------------------");
		System.out.println(title);
		System.out.println("--------------------------");
	}

	public default void showMessage(String title) {
		System.out.println();
		System.out.println(title);
		System.out.println("--------------------------");
		System.out.println();
	}

	public default UserInput input() {
		return UserInput.getInstance();
	}

}