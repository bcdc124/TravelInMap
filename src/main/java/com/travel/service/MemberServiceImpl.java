package com.travel.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.travel.dto.NaverProfileDTO;
import com.travel.entity.Member;
import com.travel.entity.TravelRecordEntity;
import com.travel.persistence.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	private final MemberRepository memberRepository;

	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean isEmailUnique(String mEmail) {
		Member existingMember = memberRepository.findBymEmail(mEmail);
		return existingMember == null;
	}

	@Override
	public String generateVerificationCode() {
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		final int CODE_LENGTH = 6;

		SecureRandom random = new SecureRandom();
		StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);

		for (int i = 0; i < CODE_LENGTH; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			codeBuilder.append(randomChar);
		}

		return codeBuilder.toString();
	}

	@Override
	public Member login(Member member) {
		Member memberResult = memberRepository.findBymEmail(member.getMEmail());
		if (memberResult != null && memberResult.getMPw().equals(member.getMPw())) {
			return memberResult;
		}

		return null;
	}

	
	@Override
	public void incrementVisits(Member member) {
        member.incrementVisits(); // 사용자 객체의 방문수 증가 메서드 호출
        memberRepository.save(member); // 변경된 정보를 데이터베이스에 저장
    }

	@Override
	public String getKakaoToken(String authorize_code) throws Exception {
		String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=61a3c6e603acd05dea0ad8967fef6f1c");
            sb.append("&redirect_uri=http://localhost:8080/member/kakao");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();
            
            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            logger.info("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            logger.info("access_token : " + access_Token);
            logger.info("refresh_token : " + refresh_Token);
            
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return access_Token;
    }

	@Override
	public HashMap<String, Object> getUserInfo(String access_token) {
//	    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String reqURL = "https://kapi.kakao.com/v2/user/me";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        
	        //    요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	        
	        int responseCode = conn.getResponseCode();
	        logger.info("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        logger.info("response body : " + result);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        
	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	        
	        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        String email = kakao_account.getAsJsonObject().get("email").getAsString();
	        
	        userInfo.put("nickname", nickname);
	        userInfo.put("email", email);
	        userInfo.put("password", generateVerificationCode());
	        
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    return userInfo;
	}

	@Override
	public void kakaoLogout(String access_token) {
		String reqURL = "https://kapi.kakao.com/v1/user/logout";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	        
	        int responseCode = conn.getResponseCode();
	        logger.info("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String result = "";
	        String line = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println(result);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}

	@Override
	public Member naverLogin(NaverProfileDTO nDTO) {
	    // 네이버 생일, 출생연도 병합
	    String birthday = nDTO.getBirthday();
	    String birthyear = nDTO.getBirthyear();
	    String fullBirth = null;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date fullBirthDate = null;

	    // birthday와 birthyear 값이 모두 존재하는 경우에만 처리
	    if (birthday != null && birthyear != null) {
	        fullBirth = birthyear + "-" + birthday;
	        try {
	            fullBirthDate = (Date) dateFormat.parse(fullBirth);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }

	    // 네이버 성별 값 처리
	    String gender = nDTO.getGender();
	    Character mGender = null;

	    if (gender != null && !gender.isEmpty()) {
	        mGender = gender.charAt(0);
	    }

	    // member 객체 처리
	    Member member = memberRepository.findBymEmail(nDTO.getEmail());
	    if (member == null) {
	        member = new Member();
	        member.setMName(nDTO.getName());
	        member.setMEmail(nDTO.getEmail());
	        member.setMNick(nDTO.getNickname());
	        member.setMProfile(nDTO.getProfileImage());
	        member.setMGender(mGender);
	        member.setMBirth(fullBirthDate);
	        member.setMPw(generateVerificationCode());
	        member = memberRepository.save(member); // Member 저장
	    }

	    return member;
	}

	@Override
    public String findEmailByNameAndBirth(String mName, Date mBirth) {
        String queryString = "select mEmail from Member m where mName = :mName AND m.mBirth = :mBirth";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("mName", mName);
        query.setParameter("mBirth", mBirth);

        try {
            return (String) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

	@Override
	public String findByPw(String mName, String mEmail) {
		String queryString = "select mPw from Member where mName = :mName AND mEmail = :mEmail";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("mName", mName);
		query.setParameter("mEmail", mEmail);
		
		return (String) query.getSingleResult();
	}


	@Override
	public List<TravelRecordEntity> trList(int mNum) {
		String queryString = "select t.tTitle from TravelRecordEntity t join Member m on t.mNum = m.mNum where t.mNum = :mNum";
		TypedQuery<TravelRecordEntity> query = entityManager.createQuery(queryString, TravelRecordEntity.class);
		query.setParameter("mNum", mNum);
		
		
		return query.getResultList();
	}

	
	
	

	

	
	

}
