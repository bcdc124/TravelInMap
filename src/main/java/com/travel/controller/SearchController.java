package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.service.RecordSearchService;


@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private RecordSearchService RecordService;
	
	@RequestMapping(value = "/List", method = RequestMethod.GET)
	public String test() throws Exception{
		
		return "/search/searchList";
	}
	
	@RequestMapping(value = "/List", method = RequestMethod.POST)
	public String List(@RequestParam(value = "t_theme", required = false) String theme,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "season", required = false) Integer season,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "personnel", required = false) Integer personnel,
			Model model) throws Exception{
		
		if(category.equals("tim_travel_record")){
			System.out.println("여행기록");
			model.addAttribute("list", RecordService.search(title, theme, season, personnel));
		}
		if(category.equals("tim_schedule")) {
			System.out.println("여행일정");
		}
		
		
		
		return "/search/searchList";
	}

}






























