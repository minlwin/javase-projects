package com.jdc.pos.service;

import static com.jdc.pos.context.SqlHelper.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

import com.jdc.pos.context.ConnectionManager;
import com.jdc.pos.dto.Sale;
import com.jdc.pos.dto.SaleDTO;
import com.jdc.pos.dto.SaleDetail;

public class SaleService {

	public void save(SaleDTO saleDto) {

		if(saleDto.getSale().getId() == 0) {
			insert(saleDto);
		} else  {
			update(saleDto);
		}
	}

	private void insert(SaleDTO saleDto) {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement saleInsert = conn.prepareStatement(sql("sale.insert"), Statement.RETURN_GENERATED_KEYS);
				PreparedStatement detailsInsert = conn.prepareStatement(sql("sale.details.insert"))){
			
			Sale sale = saleDto.getSale();
			saleInsert.setString(1, sale.getSalePersonId());
			saleInsert.setDate(2, Date.valueOf(sale.getSaleDate()));
			saleInsert.setTime(3, Time.valueOf(sale.getSaleTime()));
			saleInsert.setInt(4, sale.getTaxRate());
			
			saleInsert.executeUpdate();
			
			ResultSet rs = saleInsert.getGeneratedKeys();
			
			while(rs.next()) {
				
				int saleId = rs.getInt(1);
				
				for(SaleDetail d  : saleDto.getDetails()) {
					
					detailsInsert.setInt(1, saleId);
					detailsInsert.setInt(2, d.getProductId());
					detailsInsert.setInt(3, d.getUnitPrice());
					detailsInsert.setInt(4, d.getQuantity());
					
					detailsInsert.addBatch();
				}
				
				detailsInsert.executeBatch();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(SaleDTO saleDto) {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement insert = conn.prepareStatement(sql("sale.details.insert"));
				PreparedStatement update = conn.prepareStatement(sql("sale.details.update"));
				PreparedStatement delete = conn.prepareStatement(sql("sale.details.delete"))){
			
			for(SaleDetail d : saleDto.getDetails()) {
				
				if(d.getId() == 0) {
					
					insert.setInt(1, saleDto.getSale().getId());
					insert.setInt(2, d.getProductId());
					insert.setInt(3, d.getUnitPrice());
					insert.setInt(4, d.getQuantity());
					insert.executeUpdate();
					
				} else {
					if(d.isDeleted()) {
						delete.setInt(1, d.getId());
						delete.executeUpdate();
					} else {
						update.setInt(1, d.getUnitPrice());
						update.setInt(2, d.getQuantity());
						update.setInt(3, d.getId());
						update.executeUpdate();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
