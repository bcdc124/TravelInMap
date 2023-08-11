package com.travel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.entity.Schedule;
import com.travel.persistence.ScheduleRepository;
import com.travel.service.LocationService;
import com.travel.service.ScheduleService;

@RestController
@RequestMapping("/schedules")
public class ScheduleRestController {

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private LocationService locationService;
	
	
	//read list
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    //read one
    @GetMapping("/{sNum}")
    public ResponseEntity<Map<String, Object>> getScheduleById(@PathVariable Integer sNum) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	Schedule schedule = scheduleRepository.findById(sNum).orElse(null);
    	map.put("schedule", schedule);
    	map.put("location", locationService.getLocastionList(schedule));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //create
    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody Map<String,Object> map) throws Exception {
    	locationService.createLocation(map, scheduleService.createSchedule(map));
    	return new ResponseEntity<>("요청 성공하고 디비 인서트 되고 코드200 뜨는데 왜 에이잭스는 실패 뜨는지 모르겠네", HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{sNum}")
    public ResponseEntity<Map<String, Object>> updateSchedule(@PathVariable Integer sNum, @RequestBody Map<String,Object> map) throws Exception {
    	Map<String, Object> respMap = new HashMap<>();
    	Schedule schedule = scheduleService.updateSchedule(sNum, map);
    	if(schedule != null) {
    		respMap.put("schedule", schedule);
    		locationService.updateLocation(map, schedule);
    		respMap.put("location", locationService.getLocastionList(schedule));
    		return new ResponseEntity<>(map, HttpStatus.OK);
    	}
        return null;
    }

    //delete
    @DeleteMapping("/{sNum}")
    public void deleteSchedule(@PathVariable Integer sNum) {
        scheduleRepository.deleteById(sNum);
    }
}
