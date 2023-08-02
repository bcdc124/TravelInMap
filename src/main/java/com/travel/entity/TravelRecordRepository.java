package com.travel.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRecordRepository extends JpaRepository<TravelRecordEntity, Long> {

}

