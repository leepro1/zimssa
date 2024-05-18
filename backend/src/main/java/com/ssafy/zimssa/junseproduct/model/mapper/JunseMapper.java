package com.ssafy.zimssa.junseproduct.model.mapper;

import com.ssafy.zimssa.junseproduct.model.dto.JunseInfoResponseDto;
import java.sql.SQLException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JunseMapper {
    void save(JunseInfoResponseDto junseInfoResponseDto) throws SQLException;



}
