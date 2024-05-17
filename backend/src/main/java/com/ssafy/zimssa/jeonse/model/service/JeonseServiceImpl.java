package com.ssafy.zimssa.jeonse.model.service;

import com.ssafy.zimssa.jeonse.model.dto.GuDong;
import com.ssafy.zimssa.jeonse.model.dto.Jeonse;
import com.ssafy.zimssa.jeonse.model.dto.response.GuDongResponseDto;
import com.ssafy.zimssa.jeonse.model.dto.response.JeonseResponseDto;
import com.ssafy.zimssa.jeonse.model.mapper.JeonseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JeonseServiceImpl implements JeonseService {

    private final JeonseMapper jeonseMapper;

    @Override
    public List<GuDongResponseDto> findAllGuDong() {
        List<GuDong> allGuDong = jeonseMapper.findAllGuDong();

        return allGuDong.stream()
                .map(GuDongResponseDto::fromGuDong)
                .collect(Collectors.toList());
    }

    @Override
    public List<JeonseResponseDto> findAllByGu(String sggCd) {
        List<Jeonse> jeonseList = jeonseMapper.findAllByGu(sggCd);

        return jeonseList.stream()
                .map(JeonseResponseDto::fromJeonse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JeonseResponseDto> findAllByDong(String sggCd, String bjdongCd) {
        List<Jeonse> jeonseList = jeonseMapper.findAllByDong(sggCd, bjdongCd);

        return jeonseList.stream()
                .map(JeonseResponseDto::fromJeonse)
                .collect(Collectors.toList());
    }
}
