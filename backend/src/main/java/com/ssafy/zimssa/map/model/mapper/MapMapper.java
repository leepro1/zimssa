package com.ssafy.zimssa.map.model.mapper;

import com.ssafy.zimssa.map.model.dto.request.MapMarkerType;
import com.ssafy.zimssa.map.model.dto.response.MapMarkerResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapMapper {

    List<MapMarkerResponseDto> findAllByMarker(MapMarkerType markerType);

}
