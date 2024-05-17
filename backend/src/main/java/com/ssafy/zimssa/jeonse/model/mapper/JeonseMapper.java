package com.ssafy.zimssa.jeonse.model.mapper;

import com.ssafy.zimssa.jeonse.model.dto.GuDong;
import com.ssafy.zimssa.jeonse.model.dto.Jeonse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JeonseMapper {

    List<GuDong> findAllGuDong();

    List<Jeonse> findAllByGu(String sggCd);

    List<Jeonse> findAllByDong(String sggCd, String bjdongCd);
}
