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

        System.out.println("service"+ like);
        likeMapper.insert(like);
    }

    @Override
    public List<Like> list(String userId) throws SQLException {

        System.out.println(userId);
        return likeMapper.list(userId);
    }

    @Override
    public void delete(String house_name) throws SQLException {

        likeMapper.delete(house_name);
    }
}
