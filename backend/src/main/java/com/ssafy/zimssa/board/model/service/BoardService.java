package com.ssafy.zimssa.board.model.service;

import com.ssafy.zimssa.board.model.BoardDto;

public interface BoardService {

	BoardDto getArticle(int articleNo) throws Exception;

	void updateHit(int articleNo) throws Exception;
}
