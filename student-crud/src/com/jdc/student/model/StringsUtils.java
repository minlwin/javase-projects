package com.jdc.student.model;

public class StringsUtils {

	public static boolean isBlank(String str) {
		return null == str  || str.isBlank();
	}
	
	public static boolean isNotBlank(String str) {
		return null != str && !str.isBlank();
	}
}
