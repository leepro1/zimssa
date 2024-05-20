package com.ssafy.zimssa.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.zimssa.board.model.BoardDto;
import com.ssafy.zimssa.board.model.mapper.BoardMapper;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}


	@Override
	@Transactional
	public void writeArticle(BoardDto boardDto) throws Exception {
		
		System.out.println("BoardServiceImpl.writeArticle........."+boardDto);
		boardMapper.writeArticle(boardDto);
	}


	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

}
