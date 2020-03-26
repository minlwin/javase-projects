package com.jdc.pos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jdc.pos.dto.Summary;
import com.jdc.pos.dto.TopItem;

public class SummaryService {

	public List<TopItem> findTopItems(String category, LocalDate from, LocalDate to) {
		List<TopItem> list = new ArrayList<>();
		return list;
	}

	public Map<String, Map<String, Integer>> findChartData(String category, LocalDate from, LocalDate to) {
		Map<String, Map<String, Integer>> result = new TreeMap<>();
		return result;
	}

	public Summary findSummary(String text, LocalDate value, LocalDate value2) {
		Summary summary = new Summary();
		return summary;
	}

}
