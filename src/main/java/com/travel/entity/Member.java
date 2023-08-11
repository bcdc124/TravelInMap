package com.travel.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 회원 테이블 tim_member
 * 
 * 회원 번호   m_num
 * 이메일 	   m_email
 * 비밀번호	   m_pw
 * 이름 	   m_name
 * 성별 	   m_gender
 * 생년월일    m_birth
 * 프로필 사진 m_profile
 * 닉네임 	   m_nick
 * 포인트 	   m_point
 * 은행 	   m_account
 * 계좌번호    m_account_num
 * 
 * @author Taejun
 *
 */

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) // JPA에서 lazy관련 에러 날 경우 사용
@Table(name="tim_member") // 테이블 지정
@Entity // 객체와 테이블 매핑
public class Member {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "m_num")
	 private Integer mNum;
	 
	 @Column(name = "m_email", unique = true)
	 private String mEmail;
	 
	 @Column(name = "m_pw")
	 private String mPw;
	 
	 @Column(name = "m_name")
	 private String mName;
	 
	 @Column(name ="m_gender")
	 private Character mGender;
	 
	 @Column(name = "m_birth")
	 private Date mBirth;
	 
	 @Column(name = "m_profile")
	 private String mProfile;

	 @Column(name = "m_nick")
	 private String mNick;

	 @Column(name = "m_point")
	 private int mPoint;
	 
	 @Column(name = "m_account")
	 private String mAccount;
	 
	 @Column(name = "m_account_num")
	 private String mAccount_num;
	 
	 @Column(name="m_regdate")
	 private Timestamp mRegdate;
	 
	 @Column(name = "m_visit_cnt")
	 private int mVisitCnt;
	 
	 @Column(name = "m_last_login_time")
	    private LocalDateTime mLastLoginTime;
	 
	 // 방문수 증가
	 public void incrementVisits() {
		 if (mLastLoginTime == null || mLastLoginTime.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
	            this.mVisitCnt++;
	        }
		 this.mLastLoginTime = LocalDateTime.now();
	    }
	 
}
