package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Test {
	
	@RequestMapping(method = RequestMethod.GET, value =  "hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping(method = RequestMethod.GET, value =  "travelRecordsList")
	public String travelRecordsList() {
		return "travelRecordsList";
	}
}
