package com.travel.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.entity.Member;
import com.travel.entity.Schedule;
import com.travel.persistence.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Override
	public void createSchedule(Map<String,Object> map) throws Exception {
		Schedule entity = new Schedule();
		Member member = new Member();
		member.setMNum(Integer.parseInt(map.get("mNum").toString()));
		entity.setMNum(member);
		entity.setSTitle(map.get("sTitle").toString());
		entity.setSStatus(map.get("sStatus").toString());
		scheduleRepository.save(entity);
	}

}
