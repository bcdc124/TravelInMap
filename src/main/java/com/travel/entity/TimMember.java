package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

//@SuperBuilder
//@NoArgsConstructor
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) // JPA에서 lazy관련 에러 날 경우 사용
@Table(name="tim_member") // 테이블 지정
@Entity // 객체와 테이블 매핑
public class TimMember {
	 @Id
	 @Column(name = "m_id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private String m_id;



}
