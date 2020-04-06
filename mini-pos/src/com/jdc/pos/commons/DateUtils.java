package com.jdc.pos.commons;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd/yyyy");
	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
	
	public static String format(LocalDate date) {
		return date.format(DATE_FORMAT);
	}
	
	public static String format(LocalTime time) {
		return time.format(TIME_FORMAT);
	}
	
}
