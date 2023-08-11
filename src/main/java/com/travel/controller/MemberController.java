package com.travel.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.travel.dto.NaverProfileDTO;
import com.travel.entity.Member;
import com.travel.persistence.MemberRepository;
import com.travel.persistence.MemberTrRepository;
import com.travel.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberTrRepository memberTrRepository;
	
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
		if (member.getMGender() == 'N') {
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

		return "redirect:/member/login";
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

	// 로그인 폼 호출
	@RequestMapping(method = RequestMethod.GET, value = "login")
	public void showLoginForm() {

	}

	// 로그인 처리
	@RequestMapping(method = RequestMethod.POST, value = "login")
	public String login(@ModelAttribute Member member, HttpSession session, Model model) {

		Member memberResult = memberService.login(member);

		if (memberResult != null) {
			logger.info("Received email: " + member.getMEmail());
			logger.info("Received password: " + member.getMPw());
			session.setAttribute("id", memberResult.getMEmail());
			logger.info("Login successful - email: " + memberResult.getMEmail());

			// 방문수 증가
			memberService.incrementVisits(memberResult);

			return "redirect:/member/index";
		} else {
			logger.info("Login failed");
			logger.info("Received email: " + member.getMEmail());
			logger.info("Received password: " + member.getMPw());
			return "redirect:/member/login";
		}
	}

	// 로그아웃
	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/member/index";
	}

	// 카카오
	@RequestMapping(method = RequestMethod.GET, value = "/kakao")
	public String kakaoCallback(@RequestParam String code, HttpSession session) throws Exception {

		String access_token = memberService.getKakaoToken(code);
		HashMap<String, Object> userInfo = memberService.getUserInfo(access_token);
		logger.info("memberController : " + userInfo);

		String email = (String) userInfo.get("email");
		Member member = memberRepository.findBymEmail(email);

		if (member != null) {
			session.setAttribute("kakaoId", member.getMEmail());
			session.setAttribute("access_token", access_token);
			memberService.incrementVisits(memberService.login(member));
		} else {
			Member newMember = new Member();
			newMember.setMEmail(email);
			newMember.setMName((String) userInfo.get("nickname"));
			newMember.setMPw((String) userInfo.get("password"));

			memberRepository.save(newMember);
			memberService.incrementVisits(memberService.login(member));

			session.setAttribute("kakaoId", newMember.getMEmail());
			logger.info("카카오 회원 저장");
		}
		logger.info("카카오 로그인 성공" + session.getAttribute("kakaoId"));
		return "redirect:/member/index";
	}

	// 네이버 콜백
	@RequestMapping(method = RequestMethod.GET, value = "/callback")
	public void naverCallback() {

	}

	// 네이버 로그인
	@RequestMapping(method = RequestMethod.POST, value = "/naver")
	public String naverLogin(@RequestBody NaverProfileDTO nDTO, HttpSession session) throws Exception {

		Member member = memberService.naverLogin(nDTO);

		if (member != null) {
			session.setAttribute("id", member);
			memberService.incrementVisits(memberService.login(member));
			logger.info("@@@@@@@nDTO : " + member);
		}

		return "redirect:/member/index";
	}

	// 이메일 찾기
	@RequestMapping(method = RequestMethod.POST, value = "/findEmail")
	@ResponseBody
	public ResponseEntity<String> findByEmail(@RequestParam String mName, @RequestParam String mBirth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date mBirthDate;
        try {
            mBirthDate = dateFormat.parse(mBirth);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        java.sql.Date mBirthSqlDate = new java.sql.Date(mBirthDate.getTime());
        String email = memberService.findEmailByNameAndBirth(mName, mBirthSqlDate);
        if (email != null) {
            return ResponseEntity.ok(email);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	// 비밀번호 찾기
	@RequestMapping(method = RequestMethod.POST, value = "/findPw")
	@ResponseBody
	public ResponseEntity<String> findByPw(@RequestParam String mName, @RequestParam String mEmail){
		
		String mPw =memberService.findByPw(mName, mEmail); 
		if(mPw != null) {
			return ResponseEntity.ok(mPw);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// 마이 페이지(여행기)
	@RequestMapping(method = RequestMethod.GET, value = "/myPage")
    public String myPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("id");
        String kakaoEmail = (String) session.getAttribute("kakaoId");
        
        int mNum = 0;
        
        if (email == null) {
            Member member = memberRepository.findBymEmail(kakaoEmail);
            if (member != null) {
                mNum = member.getMNum();
                model.addAttribute("member", memberRepository.findBymEmail(kakaoEmail));
            }
        } else {
            Member member = memberRepository.findBymEmail(email);
            if (member != null) {
                mNum = member.getMNum();
                model.addAttribute("member", memberRepository.findBymEmail(email));
            }
        }

        model.addAttribute("trList", memberTrRepository.findBymNum(mNum)); // 변경된 부분
        
        return "/member/myPage";
    }
	
	// 회원 정보 출력
	@RequestMapping(method = RequestMethod.GET, value = "/userInfo")
	public String userInfo(HttpSession session, Model model) {
	    String email = (String) session.getAttribute("id");
	    String kakaoEmail = (String) session.getAttribute("kakaoId");
	    
	    if (email == null && kakaoEmail == null) {
	        return "redirect:/member/login";
	    }
	    
	    if(email == null) {
	    	model.addAttribute("member", memberRepository.findBymEmail(kakaoEmail));
	    	return "/member/userInfo";
	    }
	    model.addAttribute("member", memberRepository.findBymEmail(email));
	    logger.info("sessionEmail@@@@@@@@@@@@@@@ : "+email);
	    
	    return "/member/userInfo";
	}
	
	// 회원 수정
	@RequestMapping(method = RequestMethod.POST, value = "/userUpdate")
	public String userUpdate(@ModelAttribute Member member) {
		memberRepository.updateMember(member);
		logger.info("userUpdate @@@@@@@@@@@@@@@"+memberRepository.updateMember(member));
		return "redirect:/member/myPage";
	}
	
	// 프로필 사진 수정
	@PostMapping("/profileImg")
	@ResponseBody
	public String profileImg(@RequestParam("mProfile") MultipartFile mFile,
			HttpSession session) throws Exception{
		Member member = new Member();
		member.setMEmail((String)session.getAttribute("id"));
		String filePath = 
				getClass().getResource("/").getPath().split("resources")[0]+"static/img/";
		String oFileName = mFile.getOriginalFilename();
		logger.info("file이름 저장 완료! ");
		logger.info("파일 경로 : "+filePath);
		 // 파일 이름에 고유한 값을 추가하여 파일 이름 변경
        String fileExtension = oFileName.substring(oFileName.lastIndexOf(".")); // 파일 확장자 추출
        String uniqueFileName = new SimpleDateFormat("HHmmss").format(new Date()) + UUID.randomUUID().toString() + fileExtension;
		
		File file = new File(filePath + uniqueFileName);
		if (mFile.getSize() != 0) {
			// 해당 경로에 파일이 없을경우
			if (!file.exists()) {
				// 해당하는 디렉터리 생성후 파일을 업로드
				if (file.getParentFile().mkdirs()) {
					file.createNewFile();
				} // mkdirs
			} // exists
				// 임시로 생성(저장) MultipartFile을 실제 파일로 전송
			mFile.transferTo(file);
		} 
		
		member.setMProfile(uniqueFileName);
		memberRepository.updateProfile(member);
		return "success";
	}
	
	// 마이페이지 - 포인트
	@RequestMapping(method = RequestMethod.GET, value = "/point")
	public void point(Model model, HttpSession session) {
		String email = (String) session.getAttribute("id");
		String kakaoEmail = (String) session.getAttribute("kakaoId");
		if(email == null) {
			model.addAttribute("member", memberRepository.findBymEmail(kakaoEmail));
			return;
		}
		model.addAttribute("member", memberRepository.findBymEmail(email));
	}
	
	// 마이페이지 - 일정
	@RequestMapping(method = RequestMethod.GET, value = "/schedule")
	public void schedule(Model model, HttpSession session) {
		String email = (String) session.getAttribute("id");
		String kakaoEmail = (String) session.getAttribute("kakaoId");
		if(email == null) {
			model.addAttribute("member", memberRepository.findBymEmail(kakaoEmail));
			return;
		}
		model.addAttribute("member", memberRepository.findBymEmail(email));
		
	}
	
	

}
