package com.ssafy.zimssa.map.model.sevice;

import com.ssafy.zimssa.map.model.dto.request.MapMarkerType;
import com.ssafy.zimssa.map.model.dto.response.MapMarkerResponseDto;
import com.ssafy.zimssa.map.model.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    @Override
    public List<MapMarkerResponseDto> findAllByMarker(MapMarkerType markerType) {
        return mapMapper.findAllByMarker(markerType);
    }
}
