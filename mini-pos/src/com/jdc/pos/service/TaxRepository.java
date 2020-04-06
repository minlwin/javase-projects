package com.jdc.pos.service;

import static com.jdc.pos.context.SqlHelper.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.jdc.pos.context.ConnectionManager;
import com.jdc.pos.dto.TaxInfo;

public class TaxRepository {

	private static TaxRepository repository;
	private Set<TaxInfo> data;
	
	private TaxRepository() {
		data = new TreeSet<>();
		loadData();
	}
	
	public void loadData() {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql("tax.select"))){
			
			data.clear();
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				data.add(new TaxInfo(rs.getDate(1).toLocalDate(), rs.getInt(2)));
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
		return data.stream()
				.filter(a -> a.getStartDate().compareTo(refDate) <= 0)
				.findFirst().map(a -> a.getPercent()).orElse(0);
	}

	public List<TaxInfo> getAll() {
		return new ArrayList<>(data);
	}

	public void save(List<TaxInfo> items) {

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement insert = conn.prepareStatement(sql("tax.insert"));
				PreparedStatement update = conn.prepareStatement(sql("tax.update"))){
			
			for(TaxInfo tax : items) {
				
				TaxInfo old = data.stream().filter(a -> a.getStartDate().equals(tax.getStartDate()))
						.findAny().orElse(null);
				
				if(old == null) {
					insert.setDate(1, Date.valueOf(tax.getStartDate()));
					insert.setInt(2, tax.getPercent());
					insert.executeUpdate();
				} else {
					update.setInt(1, tax.getPercent());
					update.setDate(2, Date.valueOf(tax.getStartDate()));
					update.executeUpdate();
				}
			}
			
			loadData();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
