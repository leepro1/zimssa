package com.ssafy.zimssa.jeonse.controller;

import com.ssafy.zimssa.jeonse.model.dto.response.GuDongResponseDto;
import com.ssafy.zimssa.jeonse.model.dto.response.JeonseResponseDto;
import com.ssafy.zimssa.jeonse.model.service.JeonseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jeonse")
public class JeonseController {

    private final JeonseService jeonseService;

    @GetMapping()
    public ResponseEntity<List<GuDongResponseDto>> searchGuDong() {
        List<GuDongResponseDto> response = jeonseService.findAllGuDong();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search-apt")
    public ResponseEntity<List<JeonseResponseDto>> searchAllByGuDong(
            @RequestParam(name = "gunCode") String sggCd,
            @RequestParam(name = "dongCode", required = false) String bjdongCd) {
        List<JeonseResponseDto> response = bjdongCd == null ? jeonseService.findAllByGu(sggCd) : jeonseService.findAllByDong(sggCd, bjdongCd);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
