package com.ssafy.zimssa.board.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.zimssa.board.model.BoardDto;


@Mapper
public interface BoardMapper {

	BoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
}
