package com.travel.dto;

import lombok.Data;

/**
 * 네이버 로그인 api로 받아오는 회원 정보를 담는 객체
 * @author Taejun
 *
 */

@Data
public class NaverProfileDTO {
	private String name;
    private String email;
    private String nickname;
    private String gender;
    private String birthday;
    private String birthyear;
    private String profileImage;
	
}
