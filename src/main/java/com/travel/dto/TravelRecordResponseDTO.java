package com.travel.dto;

import java.time.LocalDateTime;

import com.travel.entity.TravelRecordEntity;

import lombok.Data;

@Data
public class TravelRecordResponseDTO {
    private long t_num;
    private String t_title;
    private int m_num;
    private LocalDateTime t_postDate;
    private int t_view;
    private int t_great;
    private String t_content;
    private String t_tag;
    private int t_personnel;
    private char t_save;
    private String t_startDay;
    private String t_endDay;
    private String t_theme;

    public TravelRecordResponseDTO (TravelRecordEntity entity) {
	    	this.t_num = entity.getT_num();
	        this.t_title = entity.getTTitle();
	        this.m_num = entity.getMNum();
	        this.t_postDate = entity.getT_postDate();
	        this.t_view = entity.getTView();
	        this.t_great = entity.getTGreat();
	        this.t_content = entity.getT_content();
	        this.t_tag = entity.getTTag();
	        this.t_personnel = entity.getT_personnel();
	        this.t_save = entity.getT_save();
	        this.t_startDay = entity.getTStartDay();
	        this.t_endDay = entity.getTEndDay();
	        this.t_theme = entity.getTTheme();
    }
}

