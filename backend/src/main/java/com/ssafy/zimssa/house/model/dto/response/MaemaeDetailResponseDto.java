package com.ssafy.zimssa.house.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaemaeDetailResponseDto {

    private String dealType; // 매매, 전세, 월세
    private String houseType; // 오피스텔, 아파트, 등
    private String area;
    private String dealDate;
    private String dealPrice;
    private String floor;
    private String lat;
    private String lng;

}
