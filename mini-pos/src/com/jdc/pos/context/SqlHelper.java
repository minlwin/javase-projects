package com.jdc.pos.context;

import java.util.Properties;

public class SqlHelper {
	
	private static Properties props;
	
	static {
		try {
			props = new Properties();
			props.load(SqlHelper.class.getResourceAsStream("sql.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String sql(String key) {
		return props.getProperty(key);
	}
}
