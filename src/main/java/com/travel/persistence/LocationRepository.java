package com.travel.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.entity.Location;
import com.travel.entity.Schedule;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	// 일정에 등록된 장소 리스트
	public List<Location> findBysNum(Schedule sNum);
	
	// 해당 일정의 장소 모두 삭제
	public void deleteBysNum(Schedule sNum);
}
