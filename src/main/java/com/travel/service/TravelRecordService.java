package com.travel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.travel.dto.TravelRecordRequestDTO;
import com.travel.dto.TravelRecordResponseDTO;
import com.travel.entity.TravelRecordEntity;

public interface TravelRecordService {
	
	// 생성
	public long save(final TravelRecordRequestDTO trDTO)throws Exception;
    
	// 리스트 조회
	public List<TravelRecordResponseDTO> listAll()throws Exception;
	// 수정
	public Long update(final Long t_num, final TravelRecordRequestDTO trDTO)throws Exception;
	// 삭제
//	public Long delete(final Long t_num)throws Exception;
	public void delete(long t_num);

	// 상세조회
	public TravelRecordResponseDTO findById(final Long t_num)throws Exception;
	
	
}

