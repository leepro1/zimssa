package com.ssafy.zimssa.map.model.dto.response;

import com.ssafy.zimssa.map.model.dto.request.MapMarkerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapMarkerResponseDto {

    private MapMarkerType type;
    private String name;
    private String lat;
    private String lng;
}
