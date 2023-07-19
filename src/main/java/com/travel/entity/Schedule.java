package com.travel.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


/**
 * 일정 테이블	tim_schedule
 * 일정글번호	s_num
 * 제목		s_title
 * 회원번호	m_num
 * 등록일		s_regdate
 * 스크랩수	s_count
 * 조회수		s_view
 * 공개/비공개	s_status
 * 
 * @author bcdc124
 *
 */
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name="tim_schedule")
@Entity
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "s_num")
	private Integer s_num;
	
	@Column(name = "s_title", length = 30, nullable = false)
	private String s_title;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_num", nullable = false)
	private TimMember member;
	
	@Column(name = "s_regdate", nullable = false)
	private Timestamp s_regdate;
	
	@Column(name = "s_count", nullable = false)
	private int s_count;
	
	@Column(name = "s_view", nullable = false)
	private int s_view;
	
	@Column(name = "s_status", length = 1, nullable = false)
	private String s_status;

}
