package com.jdc.pos.context;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {

	
	private static String URL;
	private static String USER = null;
	private static String PASS = null;
	
	static {
		try {
			
			Properties prop = new Properties();
			prop.load(new FileInputStream("connection.properties"));
			
			URL = prop.getProperty("app.connection.url");
			USER = prop.getProperty("app.connection.user");
			PASS = prop.getProperty("app.connection.pass");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
