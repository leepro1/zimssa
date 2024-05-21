package com.ssafy.zimssa.likelist.model.service;

import com.ssafy.zimssa.likelist.model.dto.Like;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.object.SqlQuery;

public interface LikeService  {


     void insert(Like like) throws SQLException;
     List<Like> list(String userId) throws SQLException;
     void delete(String house_name) throws SQLException;

}
