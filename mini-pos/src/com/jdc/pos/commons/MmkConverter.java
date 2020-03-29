package com.jdc.pos.commons;

import java.text.DecimalFormat;

import javafx.util.StringConverter;

public class MmkConverter extends StringConverter<Number>{
	
	private static DecimalFormat DF = new DecimalFormat("#,##0 MMK");

	@Override
	public Number fromString(String str) {
		
		try {
			if(!StringUtils.isEmpty(str)) {
				return DF.parse(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String toString(Number num) {
		
		try {
			
			if(null != num) {
				return DF.format(num);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
