package com.ssafy.zimssa.house.model.sevice;

import com.ssafy.zimssa.house.model.dto.request.AptDetailRequestDto;
import com.ssafy.zimssa.house.model.dto.request.KeywordRequestDto;
import com.ssafy.zimssa.house.model.dto.response.JeonseDetailResponseDto;
import com.ssafy.zimssa.house.model.dto.response.KeywordResponseDto;
import com.ssafy.zimssa.house.model.dto.response.MaemaeDetailResponseDto;
import com.ssafy.zimssa.house.model.mapper.HouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseMapper houseMapper;

    @Override
    public List<KeywordResponseDto> findAllAptByKeyword(KeywordRequestDto keywordDto) {

        return houseMapper.findAllAptByKeyword(keywordDto);
    }

    @Override
    public List<MaemaeDetailResponseDto> findAllAptDetailByMaeMae(AptDetailRequestDto aptDetailDto) {
        return houseMapper.findAllAptDetailByMaeMae(aptDetailDto);
    }

    @Override
    public List<JeonseDetailResponseDto> findAllAptDetailByJeonse(AptDetailRequestDto aptDetailDto) {
        return houseMapper.findAllAptDetailByJeonse(aptDetailDto);
    }

}
