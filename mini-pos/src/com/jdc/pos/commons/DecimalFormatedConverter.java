package com.jdc.pos.commons;

import java.text.DecimalFormat;

import javafx.util.StringConverter;

public class DecimalFormatedConverter extends StringConverter<Integer>{

	private static final DecimalFormat DF = new DecimalFormat("#,##0");
	
	@Override
	public Integer fromString(String str) {
		try {
			if(!StringUtils.isEmpty(str)) {
				return DF.parse(str).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString(Integer data) {
		
		if(null != data) {
			return DF.format(data);
		}
		
		return null;
	}

}
