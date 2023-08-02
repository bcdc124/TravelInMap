package com.travel.service;

import java.util.Map;

public interface ScheduleService {
	// 일정 등록
	public void createSchedule(Map<String,Object> map) throws Exception;
}
