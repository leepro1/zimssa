package com.ssafy.zimssa.member.model.service;

import com.ssafy.zimssa.member.model.dto.MailDto;
import com.ssafy.zimssa.member.model.dto.MemberChangeDto;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssafy.zimssa.member.model.dto.Member;
import com.ssafy.zimssa.member.model.mapper.MemberMapper;

import lombok.Setter;

@Service
public class MemberServiceImpl implements MemberService{


	private MemberMapper memberMapper;

	@Autowired
	private JavaMailSender mailSender;
	




	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		
		this.memberMapper = memberMapper;
	}

	
	
	
	
	@Override
	public Member login(Member member) throws Exception {
		return memberMapper.login(member);
	}

	@Override
	public void join(Member member) throws Exception {

		 memberMapper.join(member);
	}

	@Override
	public void delete(String id) throws Exception {

		System.out.println("impl");
		memberMapper.delete(id);
	}

	@Override
	public void update(Member member) throws Exception {
		
		
		
		memberMapper.update(member);
		
	}
	
	@Override
	public Member userInfo(String userId) throws Exception {
		return memberMapper.userInfo(userId);
	}

	@Override
	public boolean checkduplicate(String id) throws Exception {
//
//		Member member = null;
//		member=memberMapper.userInfo(id);
//		System.out.println(member);

		String check_id ="empty";
		check_id=memberMapper.checkdup(id);
	//	System.out.println(check_id);
		if(check_id==null)
			return false;
		else return true;
	}

	@Override
	public void userCheck(String user_id) throws SQLException {
		System.out.println(user_id);
		Member member = memberMapper.getMember(user_id);

		System.out.println("userCheck: "+member);
		if(member == null)
		{
			System.out.println("불가");
			throw new RuntimeException("유효하지 않은 아이디입니다. 다시 입력하세요");

				}
		else {

			sendEamail(member);
		}
	}

	@Override
	public void sendEamail(Member member) throws SQLException {

		MailDto mailDto = createMailAndChargePassword(member);

		System.out.println("이메일 전송 완료");
		SimpleMailMessage message = new SimpleMailMessage();



		message.setTo(mailDto.getAddress());
		message.setFrom("wjdals05070@gmail.com");
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());
		System.out.println(message);
		mailSender.send(message);


	}


	public MailDto createMailAndChargePassword(Member member) throws SQLException {
		String str = getTempPassword();
		MailDto dto = new MailDto();
		dto.setAddress(member.getEmailId()+"@"+member.getEmailDomain());
		dto.setTitle(member.getId()+"님의 임시비밀번호 안내 이메일 입니다.");
		dto.setMessage("안녕하세요. 짐싸 입니다.  "+ "\n"+ "임시비밀번호 안내 관련 메일 입니다." + "\n[" + member.getName() + "]" + "님의 임시 비밀번호는 "
				+ str + " 입니다.");

		System.out.println(dto);

		MemberChangeDto memberChangeDto = new MemberChangeDto(member.getId(),str);
		System.out.println(memberChangeDto);
		memberMapper.updatePassword(memberChangeDto);
		System.out.println();

		return dto;
	}


	public String getTempPassword() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		String str = "";

		int idx = 0;
		for (int i=0; i<10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}
	@Override
	public void saveRefreshToken(String id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String id) throws Exception {
		return memberMapper.getRefreshToken(id);
	}

	@Override
	public void deleRefreshToken(String id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}



	
}
