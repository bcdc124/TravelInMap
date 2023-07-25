package com.travel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@JsonIgnoreProperties("t")
@Table(name = "tim_travel_record")
public class TravelRecordEntity {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가 
	 @Column(name = "t_num") // 데이터베이스 컬럼명과 매핑
	 private long tNum;
	 
	 @Column(name = "t_title")
     private String tTitle;
	 
	 @Column(name = "m_num")
  	 private int mNum;
	 
	 @Column(name = " t_postDate")
 	 private LocalDateTime t_postDate;
	 
	 @Column(name = "t_view")
 	 private int tView;
	 
	 @Column(name = "t_great")
     private int tGreat;
     private String t_content;
     
     @Column(name= "t_tag")
     private String tTag;
     private int t_personnel;
     private char t_save;
     
     @Column(name = "t_startDay")
     private LocalDate tStartDay;
     
     @Column(name = "t_endDay")
     private LocalDate tEndDay;
     
     @Column(name = "t_theme")
     private String tTheme;
	 
     @Builder
     public TravelRecordEntity(long t_num, String t_title, int m_num, LocalDateTime t_postDate, int t_view,
             int t_great, String t_content, String t_tag, int t_personnel, char t_save,
             LocalDate t_startDay, LocalDate t_endDay, String t_theme) {
  
    	 this.tNum = t_num;
         this.tTitle = t_title;
         this.mNum = m_num;
         this.t_postDate = t_postDate;
         this.tView = t_view;
         this.tGreat = t_great;
         this.t_content = t_content;
         this.tTag = t_tag;
         this.t_personnel = t_personnel;
         this.t_save = t_save;
         this.tStartDay = t_startDay; // t_startDay에 기본값으로 현재 날짜 설정 (t_startDay != null) ? t_startDay : LocalDate.now()
         this.tEndDay = t_endDay;
         this.tTheme = t_theme;
     }
	    
	    // 게시글 수정
	    public void update(long t_num,String t_title,int m_num,LocalDateTime t_postDate,String t_content, String t_tag, int t_personnel, char t_save, 
	    					LocalDate t_startDay, LocalDate t_endDay, String t_theme) {
			this.tNum = t_num;
	    	this.tTitle = t_title;
	    	this.mNum = m_num;
	    	this.t_postDate = t_postDate;
			this.t_content = t_content;
			this.tTag = t_tag;
			this.t_personnel = t_personnel;
			this.t_save = t_save;
			this.tStartDay = t_startDay;
			this.tEndDay = t_endDay;
			this.tTheme = t_theme;
	    }
	    
	    public Long getT_num() {
	        return tNum;
	    }
}
