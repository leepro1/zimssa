package com.ssafy.zimssa.member.model.service;

import com.ssafy.zimssa.member.model.dto.Member;

public interface MemberService {

	Member login(Member memberDto) throws Exception;
	void join(Member member) throws Exception;
	void delete(String id) throws Exception;
	void update(Member member) throws Exception;
	Member userInfo(String id) throws Exception;
	
	
	
	
	
	void saveRefreshToken(String id, String refreshToken) throws Exception;
	Object getRefreshToken(String id) throws Exception;
	void deleRefreshToken(String id) throws Exception;
}
