package com.travel.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	Member findBymEmail(String mEmail);
}
