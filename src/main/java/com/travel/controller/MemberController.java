package com.travel.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.entity.Member;
import com.travel.persistence.MemberRepository;
import com.travel.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberService memberService;

	@RequestMapping(method = RequestMethod.GET, value = "index")
	public void index() {

	}

	// 회원가입 페이지 호출
	@RequestMapping(method = RequestMethod.GET, value = "signup")
	public void showSignUpForm() {

	}

	// 회원 가입 처리
	@RequestMapping(method = RequestMethod.POST, value = "signup")
	public String signUp(@ModelAttribute Member member, Model model, @RequestParam("mBirth") String mBirth) {
		if(member.getMGender() == 'N') {
			member.setMGender(null);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date parsedDate = dateFormat.parse(mBirth);
			
			member.setMBirth(new java.sql.Date(parsedDate.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		memberRepository.save(member);

		return "redirect:/member/index";
	}

	// 이메일 인증
	@RequestMapping(method = RequestMethod.POST, value = "/sendEmailVerification")
	@ResponseBody
	public ResponseEntity<String> sendEmailVerification(@RequestParam("mEmail") String mEmail) {
	    try {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(mEmail);
	        message.setSubject("여행 in 지도 회원가입 인증 번호");
	        String verificationCode = memberService.generateVerificationCode(); // 인증번호 생성 (랜덤 문자열 등)
	        
	        message.setText(verificationCode);
	        
	        javaMailSender.send(message);
	        return ResponseEntity.ok(verificationCode); // JSON 형태로 응답 (인증번호를 문자열로 반환)
	    } catch (MailException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
	    }
	}

	// 이메일 중복 체크
	@PostMapping("/checkEmail")
	public ResponseEntity<String> checkEmail(@RequestParam("mEmail") String mEmail) {
		if (memberRepository.findBymEmail(mEmail) != null) {
			return new ResponseEntity<>("duplicate_email", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("", HttpStatus.OK);
		}
	}


}
