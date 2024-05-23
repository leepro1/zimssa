package com.ssafy.zimssa.house.model.mapper;

import com.ssafy.zimssa.house.model.dto.request.AptDetailRequestDto;
import com.ssafy.zimssa.house.model.dto.request.KeywordRequestDto;
import com.ssafy.zimssa.house.model.dto.response.JeonseDetailResponseDto;
import com.ssafy.zimssa.house.model.dto.response.KeywordResponseDto;
import com.ssafy.zimssa.house.model.dto.response.MaemaeDetailResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseMapper {

    List<KeywordResponseDto> findAllAptByKeyword(KeywordRequestDto keywordDto);

    List<MaemaeDetailResponseDto> findAllAptDetailByMaeMae(AptDetailRequestDto aptDetailDto);

    List<JeonseDetailResponseDto> findAllAptDetailByJeonse(AptDetailRequestDto aptDetailDto);
}
