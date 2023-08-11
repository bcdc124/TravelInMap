package com.travel.persistence;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	Member findBymEmail(String mEmail);
	
	default Member updateMember(Member member) {
	    Member memberUpdate = findBymEmail(member.getMEmail());

	    if (memberUpdate != null) {
	        // 이메일은 변경하지 않고, 다른 정보만 업데이트
	        memberUpdate.setMGender(member.getMGender());
	        memberUpdate.setMNick(member.getMNick());
	        memberUpdate.setMPw(member.getMPw());

	        return save(memberUpdate); // 기존 memberUpdate를 저장
	    }

	    return null;
	}
	
	default Member updateProfile(Member member) {
		Member memberProfile = findBymEmail(member.getMEmail()); 
		if(memberProfile != null) {
			memberProfile.setMProfile(member.getMProfile());
			
			return save(memberProfile);
		}
		
		return null;
	}
	
	
}
