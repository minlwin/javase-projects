package com.jdc.pos.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.jdc.pos.context.ConnectionManager;
import static com.jdc.pos.context.SqlHelper.sql;

public class CategoryRepository {

	private static CategoryRepository repository;

	private Set<String> categories = new TreeSet<>();

	private CategoryRepository() {
		categories = new TreeSet<>();
		reload();
	}

	public void reload() {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql("category.findAll"))){
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				categories.add(rs.getString(1));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> search(String name) {
		return categories.stream().filter(a -> a.toLowerCase().startsWith(name.toLowerCase())).limit(10)
				.collect(Collectors.toList());
	}

	public static CategoryRepository getRepository() {
		
		if(null == repository) {
			repository = new CategoryRepository();
		}
		
		return repository;
	}

}
