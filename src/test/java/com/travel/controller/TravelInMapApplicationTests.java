package com.travel.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.travel.entity.TravelRecordEntity;
import com.travel.entity.TravelRecordRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TravelInMapApplicationTests {

	@Autowired
	TravelRecordRepository recordRepository;
	
	LocalDate startDate = LocalDate.of(2015, 1, 1);
	LocalDate endDate = LocalDate.of(2015, 1, 5);
	@Test
	void save() {
		TravelRecordEntity params = TravelRecordEntity.builder()
//			    .t_num(1)
			    .t_title("제목")
			    .m_num(2)
			    .t_postDate(LocalDateTime.now())
			    .t_view(10)
			    .t_great(1)
			    .t_content("내용")
			    .t_tag("태그")
			    .t_personnel(3)
			    .t_save('Y')
//			    .t_startDay(startDate)
//			    .t_endDay(endDate)
			    .t_theme("여행 테마")
			    .build();
		
		// 저장
		recordRepository.save(params);
		
		// 1번 게시글 정보 조회
		TravelRecordEntity entity = recordRepository.findById((long)1).get();
		assertThat(entity.getT_num()).isEqualTo(1);
//		assertThat(entity.getM_num()).isEqualTo(1);
//		assertThat(entity.getT_title()).isEqualTo("1번 제목");
		assertThat(entity.getT_content()).isEqualTo("1번 내용");
		
	}
	
	@Test
	void findAll() {
		
		// 전체 게시글 조회
		long travelRecord = recordRepository.count();
		
		// 전체 게시글 리스트 조회
		List<TravelRecordEntity> travelRecords = recordRepository.findAll();
		
	}
	
	@Test
	void delete() {
		
		// 게시글 조회
		TravelRecordEntity entity = recordRepository.findById((long) 1).get();
		// 게시글 삭제
		recordRepository.delete(entity);
		
		
	}
	
	
	
}