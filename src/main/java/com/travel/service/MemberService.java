package com.travel.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import com.travel.dto.NaverProfileDTO;
import com.travel.entity.Member;
import com.travel.entity.TravelRecordEntity;

public interface MemberService {
	
	// 이메일 중복체크
	public boolean isEmailUnique(String mEmail);
	
	// 인증번호 랜덤 생성
	public String generateVerificationCode();
	
	// 로그인 처리
	public Member login(Member member);
	
	// 방문수 증가
	public void incrementVisits(Member member);
	
	// 카카오 로그인 토큰
	public String getKakaoToken(String code) throws Exception;
	
	// 카카오 로그인 저장
	public HashMap<String, Object> getUserInfo(String access_token);
	
	// 카카오 로그아웃
	public void kakaoLogout(String access_token);
	
	// 네이버 로그인
	public Member naverLogin(NaverProfileDTO nDTO);
	
	// 이메일 찾기
	public String findEmailByNameAndBirth(String mName, Date mBirth);
	
	// 비밀번호 찾기
	public String findByPw(String mName, String mEmail);
	
	// 여행기 목록 출력
	public List<TravelRecordEntity> trList(int mNum);
}
