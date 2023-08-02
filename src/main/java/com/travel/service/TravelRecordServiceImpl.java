package com.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.travel.dto.TravelRecordRequestDTO;
import com.travel.dto.TravelRecordResponseDTO;
import com.travel.entity.TravelRecordEntity;
import com.travel.entity.TravelRecordRepository;
import com.travel.utils.CustomException;
import com.travel.utils.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 클래스내에 final로 선언된 모든 멤버에 생성자를 만들어줌!!!!!
public class TravelRecordServiceImpl implements TravelRecordService {

	private final TravelRecordRepository trRepository;
	
	
	//  생성
	@Transactional // JPA사용 시 필수, 메서드의 실행,종료,예외 기준으로 각각 실행,종료,예외(rollback) 자동으로 처리
	@Override
	public long save(TravelRecordRequestDTO tDTO) throws Exception {
		TravelRecordEntity tEntity = trRepository.save(tDTO.toEntity());
		return tEntity.getT_num();
	}

	//  리스트 조회
	@Override
	public List<TravelRecordResponseDTO> listAll() throws Exception {
		Sort sort = Sort.by(Direction.DESC, "tNum");
		List<TravelRecordEntity> trList = trRepository.findAll(sort);
		return trList.stream().map(TravelRecordResponseDTO::new).collect(Collectors.toList());
	            
	}
	
	// 수정
	@Override
	@Transactional 
	public Long update(Long t_num, TravelRecordRequestDTO trDTO) throws Exception {
		// update쿼리 실행 메서드 X -> 메서드 종료(commit)시 update쿼리 자동으로 실행 => JPA영속성 컨텍스트 
		// JPA 엔티티 매니저 -> 엔티티 생성, 조회 시점에 영속성 컨텍스트에 엔티티 보관 및 관리
		// 엔티티 조회 시 영속성 컨텍스트에 보관(포함) 영속성 컨텍스트에 포함된 엔티티 값 변경 시 
		// 트랜잭션이 종료(commit)되는 시점에 update쿼리 실행
		// 영속성 컨텍스트에 의해 더티 체킹 > 
		// 더티체킹 = 자동으로 쿼리 실행 

		TravelRecordEntity entity = trRepository.findById(t_num).orElseThrow(() ->new CustomException(ErrorCode.POSTS_NOT_FOUND));
		
		entity.update(trDTO.getT_num(),trDTO.getT_title(),trDTO.getM_num() ,trDTO.getT_postDate(),
					trDTO.getT_content(),trDTO.getT_tag(),trDTO.getT_personnel(),trDTO.getT_save(),
					trDTO.getT_startDay(),trDTO.getT_endDay(),trDTO.getT_theme());
		
		return t_num;
	}

	  // 삭제 
	@Transactional
	public void delete(long t_num) {
        // t_num을 이용하여 데이터베이스에서 해당 게시글을 찾아 삭제합니다.
        trRepository.deleteById(t_num);
    }

	// 상세 조회
	@Override
	@Transactional
	public TravelRecordResponseDTO findById(Long t_num) throws Exception {
		TravelRecordEntity entity = trRepository.findById(t_num).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
		//조회 수 증가
		entity.increaseView();
		
		return new TravelRecordResponseDTO(entity);
	}
	
	
	
	
}
