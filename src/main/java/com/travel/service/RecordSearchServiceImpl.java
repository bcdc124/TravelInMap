package com.travel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.entity.TravelRecordEntity;
import com.travel.persistence.RecordSearchQueryRepository;
import com.travel.persistence.RecordSearchRepository;

@Service
public class RecordSearchServiceImpl implements RecordSearchService {
	
	@Autowired
	private RecordSearchRepository repo;
	
	@Autowired
	private RecordSearchQueryRepository rq;

	@Override
	public List<TravelRecordEntity> search(String title, String theme, Integer season, Integer personnel) {
		return rq.findBytTitleContaining(title, theme, season, personnel);
	}

}
