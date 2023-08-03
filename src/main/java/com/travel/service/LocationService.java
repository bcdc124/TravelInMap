package com.travel.service;

import java.util.Map;

public interface LocationService {
	// 장소 등록
	public void createLocation(Map<String,Object> map) throws Exception;
}
