package com.travel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.entity.Location;
import com.travel.persistence.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationrepository;
	
	@Override
	public void createLocation(Map<String, Object> map) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		List list = (List) objectMapper.readValue(map.get("placeData").toString(), Location.class);
		Location location = new Location();
		System.out.println(list);
//		locationrepository.save(location);
	}

}
