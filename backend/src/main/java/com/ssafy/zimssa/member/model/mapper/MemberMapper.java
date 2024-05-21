package com.ssafy.zimssa.member.model.mapper;

import com.ssafy.zimssa.member.model.dto.MemberChangeDto;
import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


import com.ssafy.zimssa.member.model.dto.Member;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface MemberMapper {

	
	
	
	
	Member login(Member Member) throws SQLException;
	Member userInfo(String id) throws SQLException;
	String checkdup(String id) throws SQLException;
	void update(Member member) throws SQLException;
	void join(Member member) throws SQLException;
	void delete(String id) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String id) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;

	Member getMember(String id) throws SQLException;
	void updatePassword(MemberChangeDto memberChangeDto) throws SQLException;
}
