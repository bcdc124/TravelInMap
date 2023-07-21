package com.travel.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.entity.Member;
import com.travel.persistence.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{

	private final MemberRepository MEMBERREPOSITORY;
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.MEMBERREPOSITORY = memberRepository;
	}

	@Override
	public boolean isEmailUnique(String mEmail) {
		Member existingMember = MEMBERREPOSITORY.findBymEmail(mEmail);
		return existingMember == null;
	}

	@Override
	public String generateVerificationCode() {
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    final int CODE_LENGTH = 6;
	    
	    SecureRandom random = new SecureRandom();
	    StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
	    
	    for(int i = 0; i<CODE_LENGTH; i++) {
	    	int randomIndex = random.nextInt(CHARACTERS.length());
	    	char randomChar = CHARACTERS.charAt(randomIndex);
	    	codeBuilder.append(randomChar);
	    }
		
		return codeBuilder.toString();
	}

	

}
