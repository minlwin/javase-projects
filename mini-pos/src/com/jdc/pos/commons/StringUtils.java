package com.jdc.pos.commons;

import java.text.DecimalFormat;

public class StringUtils {
	
	private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0");

	public static boolean isEmpty(String str) {
		return null == str || str.isEmpty();
	}
	
	public static String mmk(int data) {
		return String.format("%s MMK", DECIMAL_FORMAT.format(data));
	}
	
	public static String kilo(int data) {
		if(data >= 10000) {
			int target = data / 10000;
			return String.format("%s K", DECIMAL_FORMAT.format(target));
		}
		
		return DECIMAL_FORMAT.format(data);
	}
}
