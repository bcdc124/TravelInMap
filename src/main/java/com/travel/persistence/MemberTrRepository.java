package com.travel.persistence;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.entity.TravelRecordEntity;

@Repository
public interface MemberTrRepository extends JpaRepository<TravelRecordEntity, Long> {
	List<TravelRecordEntity> findBymNum(int mNum);
}

