package com.ssafy.zimssa.house.model.sevice;

import com.ssafy.zimssa.house.model.dto.request.AptDetailRequestDto;
import com.ssafy.zimssa.house.model.dto.request.KeywordRequestDto;
import com.ssafy.zimssa.house.model.dto.response.JeonseDetailResponseDto;
import com.ssafy.zimssa.house.model.dto.response.KeywordResponseDto;
import com.ssafy.zimssa.house.model.dto.response.MaemaeDetailResponseDto;

import java.util.List;

public interface HouseService {


    List<KeywordResponseDto> findAllAptByKeyword(KeywordRequestDto keywordDto);

    List<MaemaeDetailResponseDto> findAllAptDetailByMaeMae(AptDetailRequestDto aptDetailDto);

    List<JeonseDetailResponseDto> findAllAptDetailByJeonse(AptDetailRequestDto aptDetailDto);

}
