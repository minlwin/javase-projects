package com.jdc.pos.service;

import static com.jdc.pos.context.SqlHelper.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jdc.pos.commons.StringUtils;
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

	public List<Sale> search(String person, LocalDate from, LocalDate to) {
		
		List<Sale> result = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder(sql("sale.select"));
		List<Object> params = new ArrayList<>();
		
		if(!StringUtils.isEmpty(person)) {
			sb.append(" and lower(s.sale_person) like ?");
			params.add(person.toLowerCase().concat("%"));
		}
		
		if(null != from) {
			sb.append(" and s.sale_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(null != to) {
			sb.append(" and s.sale_date <= ?");
			params.add(Date.valueOf(to));
			
		}
		
		sb.append(" group by s.id");
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())){
			
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Sale sale = new Sale();
				sale.setId(rs.getInt(1));
				sale.setSaleDate(rs.getDate(2).toLocalDate());
				sale.setSaleTime(rs.getTime(3).toLocalTime());
				sale.setTaxRate(rs.getInt(4));
				sale.setRemark(rs.getString(5));
				sale.setSalePersonId(rs.getString(6));
				sale.setSalePerson(rs.getString(7));
				sale.setQuantity(rs.getInt(8));
				sale.setSubTotal(rs.getInt(9));
				
				result.add(sale);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<SaleDetail> searchDetails(String category, LocalDate from, LocalDate to) {
		
		List<SaleDetail> result = new ArrayList<>();

		StringBuilder sb = new StringBuilder(sql("sale.details.select"));
		List<Object> params = new ArrayList<>();
		
		if(!StringUtils.isEmpty(category)) {
			sb.append(" and lower(p.category) like ?");
			params.add(category.toLowerCase().concat("%"));
		}
		
		if(null != from) {
			sb.append(" and s.sale_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(null != to) {
			sb.append(" and s.sale_date <= ?");
			params.add(Date.valueOf(to));
			
		}

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())){
			
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				SaleDetail d = new SaleDetail();
				
				d.setId(rs.getInt(1));
				d.setSaleId(rs.getInt(2));
				d.setUnitPrice(rs.getInt(3));
				d.setQuantity(rs.getInt(4));
				d.setSaleDate(rs.getDate(5).toLocalDate());
				d.setProductId(rs.getInt(6));
				d.setCategory(rs.getString(7));
				d.setProductName(rs.getString(8));
				d.setTax(rs.getInt(9));
				
				result.add(d);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

				
		return result;
	}

}
