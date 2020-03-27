package com.jdc.pos.service;

import static com.jdc.pos.context.SqlHelper.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import com.jdc.pos.context.ConnectionManager;

public class TaxRepository {

	private static TaxRepository repository;
	private Map<LocalDate, Integer> data;
	
	private TaxRepository() {
		data = new TreeMap<>();
		loadData();
	}
	
	public void loadData() {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql("tax.select"))){
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				data.put(rs.getDate(1).toLocalDate(), rs.getInt(2));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TaxRepository getRepository() {
		
		if(null == repository) {
			repository = new TaxRepository();
		}
		
		return repository;
	}
	
	public int tax(LocalDate refDate) {
		return data.entrySet().stream()
				.filter(a -> a.getKey().compareTo(refDate) <= 0)
				.findFirst().map(a -> a.getValue()).orElse(0);
	}
}
