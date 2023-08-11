package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder.Default;
import lombok.Data;


/**
 * 일정 테이블	tim_schedule
 * 일정글번호	sNum
 * 제목		sTitle
 * 회원번호	mNum
 * 등록일		sRegdate
 * 스크랩수	sCount
 * 조회수		sView
 * 공개/비공개	sStatus
 * 여행 시작일	sStart
 * 여행 종료일	sEnd
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
	private Integer sNum;
	
	@Column(name = "s_title", length = 30, nullable = false)
	private String sTitle;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_num", nullable = false)
	private Member mNum;
	
	@Column(name = "s_regdate", nullable = true)
	private String sRegdate;
	
	@Column(name = "s_count", nullable = false)
	private int sCount;
	
	@Column(name = "s_view", nullable = false)
	private int sView;
	
	@Column(name = "s_status", length = 1, nullable = false)
	private String sStatus;
	
	@Column(name = "s_start", nullable = false)
	private String sStart;
	
	@Column(name = "s_end", nullable = false)
	private String sEnd;

}
