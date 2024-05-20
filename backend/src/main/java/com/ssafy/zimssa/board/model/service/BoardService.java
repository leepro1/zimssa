package com.ssafy.zimssa.board.model.service;

import com.ssafy.zimssa.board.model.BoardDto;

public interface BoardService {

	// 글 작성 
	void writeArticle(BoardDto boardDto) throws Exception;

	// 글 가져오기
	BoardDto getArticle(int articleNo) throws Exception;

	// 글 조회수 증가
	void updateHit(int articleNo) throws Exception;
}
