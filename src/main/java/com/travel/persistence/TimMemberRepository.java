package com.travel.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.TimMember;

public interface TimMemberRepository extends JpaRepository<TimMember, String>{

}
