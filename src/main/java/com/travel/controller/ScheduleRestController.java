package com.travel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Integer id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    //create
    @PostMapping
    public String createSchedule(@RequestBody Map<String,Object> map) throws Exception {
    	scheduleService.createSchedule(map);
    	locationService.createLocation(map);
    	return "?";
    }

    //update
    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Integer id, @RequestBody Schedule updatedSchedule) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        if (schedule != null) {
            schedule.setSTitle(updatedSchedule.getSTitle());

            return scheduleRepository.save(schedule);
        }
        return null;
    }

    //delete
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Integer id) {
        scheduleRepository.deleteById(id);
    }
}
