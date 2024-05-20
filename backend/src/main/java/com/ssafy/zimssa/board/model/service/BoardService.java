package com.ssafy.zimssa.board.model.service;

import java.util.Map;

import com.ssafy.zimssa.board.model.BoardDto;
import com.ssafy.zimssa.board.model.BoardListDto;

public interface BoardService {

	// 글 작성 
	void writeArticle(BoardDto boardDto) throws Exception;

	// 글 가져오기
	BoardDto getArticle(int articleNo) throws Exception;

	// 글 조회수 증가
	void updateHit(int articleNo) throws Exception;

	// 전체 글 조회 
	BoardListDto listArticle(Map<String, String> map) throws Exception;

	// 글 삭제
	void deleteArticle(int articleNo) throws Exception;

	// 글 수정 
	void modifyArticle(BoardDto boardDto) throws Exception;


}
