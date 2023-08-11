package com.travel.dto;

import java.time.LocalDateTime;

import com.travel.entity.TravelRecordEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TravelRecordRequestDTO {
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

    public TravelRecordEntity toEntity() {
    	return TravelRecordEntity.builder()
//    			.t_num(t_num)
		    	.t_title(t_title)
		    	.m_num(m_num)
		        .t_postDate(t_postDate)
		        .t_view(t_view)
		        .t_great(t_great)
		        .t_content(t_content)
		        .t_tag(t_tag)
		        .t_personnel(t_personnel)
		        .t_save(t_save)
		        .t_startDay(t_startDay)
		        .t_endDay(t_endDay)
		        .t_theme(t_theme)
		        .build();
    }
}

