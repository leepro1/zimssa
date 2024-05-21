package com.ssafy.zimssa.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.zimssa.member.model.dto.Member;
import com.ssafy.zimssa.member.model.mapper.MemberMapper;

import lombok.Setter;

@Service
public class MemberServiceImpl implements MemberService{


	private MemberMapper memberMapper;

	
	
	
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
