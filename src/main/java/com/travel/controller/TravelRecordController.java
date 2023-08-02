package com.travel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.dto.TravelRecordRequestDTO;
import com.travel.service.TravelRecordService;

@Controller
@RequestMapping("/travelrecords")
public class TravelRecordController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 


	private TravelRecordService travelRecordService;
	
	// 리스트
	@GetMapping("/list")
	public String travelRecordList() throws Exception {
	    return "travelrecords/list";
	}
	
	// 등록,수정
	@GetMapping("/posts")
	public String write(@RequestParam(required = false)final Long t_num, Model model) throws Exception {
		model.addAttribute("t_num",t_num);
		TravelRecordRequestDTO trDTO = new TravelRecordRequestDTO();
		logger.info(trDTO+"@@@@@@@@@@@@@@@");
		return "travelrecords/posts";
	}
	
	// 상세페이지
	@GetMapping("/{t_num}")
	public String view(@PathVariable final Long t_num, Model model) throws Exception {
		model.addAttribute("t_num", t_num);
		
		return "travelrecords/view";
	}

}
