package com.ssafy.zimssa.house.controller;

import com.ssafy.zimssa.house.model.dto.request.AptDetailRequestDto;
import com.ssafy.zimssa.house.model.dto.request.KeywordRequestDto;
import com.ssafy.zimssa.house.model.dto.request.TradeType;
import com.ssafy.zimssa.house.model.dto.response.GuDongResponseDto;
import com.ssafy.zimssa.house.model.dto.response.JeonseDetailResponseDto;
import com.ssafy.zimssa.house.model.dto.response.KeywordResponseDto;
import com.ssafy.zimssa.house.model.dto.response.MaemaeDetailResponseDto;
import com.ssafy.zimssa.house.model.sevice.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/house")
public class HouseController {

    private final HouseService houseService;

    @GetMapping("/searchGuDong")
    public ResponseEntity<List<GuDongResponseDto>> searchGuDong() {
        List<GuDongResponseDto> response = houseService.findAllGuDong();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<KeywordResponseDto>> searchByKeyword(@ModelAttribute KeywordRequestDto keywordDto) {
        List<KeywordResponseDto> response = houseService.findAllAptByKeyword(keywordDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> searchDetail(@ModelAttribute AptDetailRequestDto aptDetailRequestDto) {
        if (TradeType.maemae_info.equals(aptDetailRequestDto.getTradeType())) {
            List<MaemaeDetailResponseDto> response = houseService.findAllAptDetailByMaeMae(aptDetailRequestDto);
            return ResponseEntity.ok(response);
        } else {
            List<JeonseDetailResponseDto> response = houseService.findAllAptDetailByJeonse(aptDetailRequestDto);
            return ResponseEntity.ok(response);
        }
    }

}
