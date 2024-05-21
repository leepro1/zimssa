package com.ssafy.zimssa.junseproduct.model.mapper;

import com.ssafy.zimssa.junseproduct.model.dto.JunseInfoResponseDto;
import java.sql.SQLException;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;


@Mapper
public interface JunseMapper {
    void save(JunseInfoResponseDto junseInfoResponseDto) throws SQLException;

    JunseInfoResponseDto getproduct(@Param("userId") String userId) throws SQLException;



}
