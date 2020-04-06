package com.jdc.pos.commons;

import com.jdc.pos.context.PosException;

public class Validations {

	public static void notEmptyInput(String data, String field) {
		if(StringUtils.isEmpty(data)) {
			throw new PosException(String.format("Please enter %s!", field));
		}
	}

	public static void notEmptySelect(String data, String field) {
		if(StringUtils.isEmpty(data)) {
			throw new PosException(String.format("Please select %s!", field));
		}
	}
	
	public static void numberField(String data, String field) {
		try {
			Integer.parseInt(data);
		} catch (Exception e) {
			throw new PosException(String.format("Please enter %s with digit.", field));
		}
	}
}
