package com.travel.service;

import java.util.List;
import java.util.Map;

import com.travel.entity.Location;
import com.travel.entity.Schedule;

public interface LocationService {
	// 장소 등록
	public void createLocation(Map<String,Object> map, Schedule sNum) throws Exception;
	
	// 일정에 등록된 장소 리스트
	public List<Location> getLocastionList(Schedule sNum) throws Exception;
	
	// 장소 업데이트
	public void updateLocation(Map<String,Object> map, Schedule sNum) throws Exception;
	
	// 장소 삭제
	public void deleteLocation(Schedule sNum) throws Exception;
}
