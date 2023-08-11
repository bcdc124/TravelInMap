package com.travel.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
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
		System.out.println(trDTO);
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

	
//	@PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
//	@ResponseBody
//	public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
//		
//		JsonObject jsonObject = new JsonObject();
//		
//		String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
//		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
//		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
//				
//		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
//		
//		File targetFile = new File(fileRoot + savedFileName);	
//		
//		try {
//			InputStream fileStream = multipartFile.getInputStream();
//			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
//			jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
//			jsonObject.addProperty("responseCode", "success");
//				
//		} catch (IOException e) {
//			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
//			jsonObject.addProperty("responseCode", "error");
//			e.printStackTrace();
//		}
//		
//		return jsonObject;
//	}
	
	@PostMapping(value = "/uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        String fileRoot = "C:\\summernote_image\\"; // 저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename(); // 오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자

        String savedFileName = UUID.randomUUID() + extension; // 저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장
            jsonObject.addProperty("url", "/summernoteImage/" + savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }
	
}
