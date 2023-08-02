package com.travel.service;

import java.util.List;

import com.travel.entity.TravelRecordEntity;

public interface RecordSearchService {
	public List<TravelRecordEntity> search(String title, String theme, Integer season, Integer personnel);

}
