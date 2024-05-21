package com.ssafy.zimssa.likelist.model.mapper;

import com.ssafy.zimssa.likelist.model.dto.Like;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    void insert(Like like) throws SQLException;
    List<Like> list(String id) throws SQLException;
     void delete(String house_name) throws SQLException;

}
