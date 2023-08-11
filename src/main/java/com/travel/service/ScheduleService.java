package com.travel.service;

import java.util.Map;

import com.travel.entity.Schedule;

public interface ScheduleService {
	// 일정 등록
	public Schedule createSchedule(Map<String,Object> map) throws Exception;
	
	// 일정 수정
	public Schedule updateSchedule(Integer sNum, Map<String,Object> map) throws Exception;
}
