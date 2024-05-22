package com.ssafy.zimssa.house.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JeonseDetailResponseDto {

    private String dealType; // 매매, 전세, 월세 -> rent_gbn
    private String houseType; // 오피스텔, 아파트, 등  -> house_gbn_nm
    private String area; // rent_area
    private String dealDate; // cntrct_de
    private String dealPrice; //rent_gtn
    private String rentPrice; // 월세 rent_fee
    private String floor; //flr_no
    private String lat;
    private String lng;
    private String buildYear;

}
