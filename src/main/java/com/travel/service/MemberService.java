package com.travel.service;

public interface MemberService {
	
	// 이메일 중복체크
	public boolean isEmailUnique(String mEmail);
	
	// 인증번호 랜덤 생성
	public String generateVerificationCode();
}
