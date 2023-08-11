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
	public Schedule createSchedule(Map<String,Object> map) throws Exception {
		Schedule schedule = new Schedule();
		Member member = new Member();
		member.setMNum(Integer.parseInt(map.get("mNum").toString()));
		schedule.setMNum(member);
		schedule.setSTitle(map.get("sTitle").toString());
		schedule.setSStatus(map.get("sStatus").toString());
		schedule.setSStart(map.get("sStart").toString());
		schedule.setSEnd(map.get("sEnd").toString());
		return scheduleRepository.save(schedule);
	}

	@Override
	public Schedule updateSchedule(Integer sNum, Map<String, Object> map) throws Exception {
		Schedule schedule = scheduleRepository.findById(sNum).orElse(null);
		if (schedule != null) {
			schedule.setSTitle(map.get("sTitle").toString());
			schedule.setSStatus(map.get("sStatus").toString());
			schedule.setSStart(map.get("sStart").toString());
			schedule.setSEnd(map.get("sEnd").toString());
			return scheduleRepository.save(schedule);
		}
		return null;
	}

}
