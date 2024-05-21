package com.ssafy.zimssa.map.controller;

import com.ssafy.zimssa.map.model.dto.request.MapMarkerType;
import com.ssafy.zimssa.map.model.dto.response.MapMarkerResponseDto;
import com.ssafy.zimssa.map.model.sevice.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;

    @GetMapping("/{keyword}")
    public ResponseEntity<List<MapMarkerResponseDto>> searchKeyword(@PathVariable MapMarkerType keyword) {
        List<MapMarkerResponseDto> response = mapService.findAllByMarker(keyword);
        return ResponseEntity.ok(response);
    }
}
