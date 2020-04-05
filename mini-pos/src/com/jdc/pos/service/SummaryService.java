package com.jdc.pos.service;

import static com.jdc.pos.commons.StringUtils.isEmpty;
import static com.jdc.pos.context.SqlHelper.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jdc.pos.context.ConnectionManager;
import com.jdc.pos.dto.Summary;
import com.jdc.pos.dto.TopItem;

public class SummaryService {

	public List<TopItem> findTopItems(String category, LocalDate from, LocalDate to) {
		List<TopItem> list = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder(sql(isEmpty(category) ? "summary.top.category" : "summary.top.product"));
		List<Object> params = new ArrayList<>();
		
		if(!isEmpty(category)) {
			sb.append(" and lower(p.category) like ?");
			params.add(category.toLowerCase().concat("%"));
		}
		
		if(null != from) {
			sb.append(" and sa.sale_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(null != to) {
			sb.append(" and sa.sale_date <= ?");
			params.add(Date.valueOf(to));
		}
		
		sb.append(" group by `key` order by `value` desc");
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				TopItem item = new TopItem();
				item.setKey(rs.getString(1));
				item.setValue(rs.getInt(2));
				list.add(item);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public Map<String, Map<LocalDate, Integer>> findChartData(String category, LocalDate from, LocalDate to) {
		Map<String, Map<LocalDate, Integer>>  result = new TreeMap<>();
		
		to = null == to ? LocalDate.now() : to;
		from = null == from ? to.minusMonths(1) : from;
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql(isEmpty(category) ? "summary.chart.category" : "summary.chart.product"))) {
			
			stmt.setDate(1, Date.valueOf(from));
			stmt.setDate(2, Date.valueOf(to));
			
			if(!isEmpty(category)) {
				stmt.setString(3, category);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				String key = rs.getString(1);
				LocalDate target = rs.getDate(2).toLocalDate();
				int value = rs.getInt(3);
				
				Map<LocalDate, Integer> map = result.get(key);
				if(null == map) {
					map = new TreeMap<>();
					result.put(key, map);
				}
				
				map.put(target, value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(from.compareTo(to) <= 0) {
			
			for(String key : result.keySet()) {
				
				Map<LocalDate, Integer> map = result.get(key);
				
				if(!map.keySet().contains(from)) {
					map.put(from, 0);
				}
			}
			
			from = from.plusDays(1);
		}
		
		return result;
	}


	public Summary findSummary(String category, LocalDate from, LocalDate to) {
		Summary summary = new Summary();
		return summary;
	}

}
