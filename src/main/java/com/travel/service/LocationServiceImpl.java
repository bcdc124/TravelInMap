package com.travel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.entity.Location;
import com.travel.entity.Schedule;
import com.travel.persistence.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;
	
	@Override
	public void createLocation(Map<String, Object> map, Schedule sNum) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(map.get("placeData").toString());
		List<List<Map<String,String>>> result = objectMapper.readValue(map.get("placeData").toString(), new TypeReference<List<List<Map<String,String>>>>() {});
		
		for(int i=0; i<result.size(); i++) {
			for(int j=0; j<result.get(i).size(); j++) {
				Location location = new Location();
				location.setSNum(sNum);
				location.setLDays(i);
				location.setLSeq(j);
				location.setLId(result.get(i).get(j).get("lId"));
				location.setLAddr(result.get(i).get(j).get("lAddr"));
				location.setLName(result.get(i).get(j).get("lName"));
				locationRepository.save(location);
			}
		}
	}

	@Override
	public List<Location> getLocastionList(Schedule sNum) throws Exception {
		return locationRepository.findBysNum(sNum);
	}

	@Override
	public void updateLocation(Map<String, Object> map, Schedule sNum) throws Exception {
		deleteLocation(sNum);
		createLocation(map, sNum);
	}

	@Override
	public void deleteLocation(Schedule sNum) throws Exception {
		locationRepository.deleteBysNum(sNum);
	}

}
