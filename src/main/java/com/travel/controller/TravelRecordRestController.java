package com.travel.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travel.dto.TravelRecordRequestDTO;
import com.travel.dto.TravelRecordResponseDTO;
import com.travel.service.TravelRecordService;

@RestController
@RequestMapping("/api")
public class TravelRecordRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TravelRecordRestController.class);

	private TravelRecordService tService;
	
//	@Value("${image.upload.dir}") // 이미지를 저장할 디렉토리 경로
//    private String imageUploadDir;
    
	
	  // 생성자 주입(Constructor Injection)
    public TravelRecordRestController(TravelRecordService tService) {
        this.tService = tService;
    }
	
	// 게시글 생성
	@PostMapping("/travelrecords")
	public Long save(@RequestBody final TravelRecordRequestDTO trDTO)throws Exception {
		return tService.save(trDTO);
	}

	// 게시글 수정 @PathVariable 변수값을 URI에 사용할 때 사용
	@PatchMapping("/travelrecords/{t_num}")
	public Long save(@PathVariable final Long t_num, @RequestBody final TravelRecordRequestDTO trDTO )throws Exception {
		logger.info(trDTO+"@@@@@@@@@@@@@@");
		return tService.update(t_num, trDTO);
	}
	
	// 게시글 삭제
	@DeleteMapping("/{t_num}")
	public void delete(@PathVariable final Long t_num)throws Exception {
		 tService.delete(t_num);
	}
	
	// 게시글 리스트 조회
	@GetMapping("/travelrecords")
	public List<TravelRecordResponseDTO> listAll()throws Exception{
		
		return tService.listAll();
	}
	
	
	// 게시글 상세조회
	@GetMapping("/travelrecords/{t_num}")
	public TravelRecordResponseDTO findById(@PathVariable Long t_num)throws Exception{
		return tService.findById(t_num);
	}
	
	 

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
        	
        	// 이미지를 업로드할 디렉토리 경로 설정
        	String imageUploadDir = "C:\\aabbccdd";

            logger.info(imageUploadDir+"asdasdasdadsads");
            
        	// 디렉토리 생성
        	File uploadDir = new File(imageUploadDir);
        	if (!uploadDir.exists()) {
        	    uploadDir.mkdirs();
        	}

            // 이미지 업로드 처리 로직
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = imageUploadDir + File.separator + fileName;
            file.transferTo(new File(filePath));

            // 이미지 URL 반환
            String imageUrl = "/images/" + fileName;
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	
}
