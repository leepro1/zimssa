package com.ssafy.zimssa.map.model.sevice;

import com.ssafy.zimssa.map.model.dto.request.MapMarkerType;
import com.ssafy.zimssa.map.model.dto.response.MapMarkerResponseDto;

import java.util.List;

public interface MapService {

    List<MapMarkerResponseDto> findAllByMarker(MapMarkerType markerType);

}
