package com.ssafy.zimssa.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


import com.ssafy.zimssa.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(Member Member) throws SQLException;
	Member userInfo(String id) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String id) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
}
