package com.jdc.pos.service;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jdc.pos.commons.StringUtils;
import com.jdc.pos.context.ConnectionManager;
import com.jdc.pos.dto.Product;

import static com.jdc.pos.context.SqlHelper.sql;

public class ProductService {

	public List<Product> search(String category, String name) {
		return search(category, name, false);
	}

	public List<Product> search(String category, String name, boolean active) {
		
		List<Product> list = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder(sql("product.select"));
		List<Object> params = new ArrayList<>();
		
		if(!StringUtils.isEmpty(category)) {
			sb.append(" and LOWER(category) like ?");
			params.add(category.toLowerCase().concat("%"));
		}
		
		if(!StringUtils.isEmpty(name)) {
			sb.append(" and LOWER(product) like ?"); 
			params.add(name.toLowerCase().concat("%"));
		}
		
		if(active) {
			sb.append(" and sold_out = ?"); 
			params.add(false);
		}
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Product p = new Product();
				list.add(p);
				
				p.setId(rs.getInt(1));
				p.setCategory(rs.getString(2));
				p.setProduct(rs.getString(3));
				p.setPrice(rs.getInt(4));
				p.setSoldOut(rs.getBoolean(5));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}


	public void changeState(Product p) {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql("product.changeState"))){
			
			stmt.setBoolean(1, !p.isSoldOut());
			stmt.setInt(2, p.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void upload(File file) {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql("product.insert"))){
			
			for(String line : Files.readAllLines(file.toPath())) {
				
				String [] array = line.split("\t");
				
				if(array.length == 3) {
					stmt.setString(1, array[0]);
					stmt.setString(1, array[3]);
					stmt.setInt(3, Integer.parseInt(array[2]));
					stmt.setBoolean(4, false);
				}
				
				stmt.addBatch();
			}
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
