package com.ssafy.zimssa.likelist.model.service;

import com.ssafy.zimssa.likelist.model.mapper.LikeMapper;
import com.ssafy.zimssa.likelist.model.dto.Like;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{


    private LikeMapper likeMapper;


    public LikeServiceImpl(LikeMapper likeMapper) {
       super();
        this.likeMapper = likeMapper;
    }

    @Override
    public void insert(Like like) throws SQLException {

        likeMapper.insert(like);
    }

    @Override
    public List<Like> list(String userId) throws SQLException {

        return likeMapper.list(userId);
    }

    @Override
    public void delete(int id) throws SQLException {

        likeMapper.delete(id);
    }
}
