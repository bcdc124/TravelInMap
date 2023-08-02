package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	@GetMapping
	public String test() {
		return "schedule/schedule";
	}
	
	@GetMapping
	@RequestMapping("/test")
	public String test2() {
		return "schedule/schedule2";
	}
}
