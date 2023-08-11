package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.entity.Schedule;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	@GetMapping
	public String test(Model model) {
		model.addAttribute("mNum", 1);
		Schedule s = new Schedule();
		s.setSNum(24);
		model.addAttribute("schedule", s);
		return "schedule/schedule";
	}
	
	@GetMapping
	@RequestMapping("/test")
	public String test2() {
		return "schedule/schedule2";
	}
}
