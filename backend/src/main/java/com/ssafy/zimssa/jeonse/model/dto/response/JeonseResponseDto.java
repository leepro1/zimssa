package com.ssafy.zimssa.jeonse.model.dto.response;

import com.ssafy.zimssa.jeonse.model.dto.Jeonse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JeonseResponseDto {

    private String houseGbnNm;
    private Float rentArea;
    private String sggCd;
    private String buildYear;
    private String rentGtn;
    private String bjdongCd;
    private String rentGbn;
    private String cntrctUpdtRqestAt;
    private Integer flrNo;
    private String sggNm;
    private String landGbn;
    private String cntrctDe;
    private String newRonSecd;
    private String bjdongNm;
    private String cntrctPrd;
    private String bubn;
    private String bldgNm;
    private String accYear;
    private String bobn;
    private String beforeGrntyAmount;
    private String landGbnNm;
    private String rentFee;
    private String beforeMtRentChrge;
    private String lat;
    private String lng;
    private String apiBuildingName;

    public static JeonseResponseDto fromJeonse(Jeonse jeonse) {
        JeonseResponseDto dto = new JeonseResponseDto();
        dto.setHouseGbnNm(jeonse.getHouseGbn_nm());
        dto.setRentArea(jeonse.getRentArea());
        dto.setSggCd(jeonse.getSggCd());
        dto.setBuildYear(jeonse.getBuildYear());
        dto.setRentGtn(jeonse.getRentGtn());
        dto.setBjdongCd(jeonse.getBjdongCd());
        dto.setRentGbn(jeonse.getRentGbn());
        dto.setCntrctUpdtRqestAt(jeonse.getCntrctUpdtRqestAt());
        dto.setFlrNo(jeonse.getFlrNo());
        dto.setSggNm(jeonse.getSggNm());
        dto.setLandGbn(jeonse.getLandGbn());
        dto.setCntrctDe(jeonse.getCntrctDe());
        dto.setNewRonSecd(jeonse.getNewRonSecd());
        dto.setBjdongNm(jeonse.getBjdongNm());
        dto.setCntrctPrd(jeonse.getCntrctPrd());
        dto.setBubn(jeonse.getBubn());
        dto.setBldgNm(jeonse.getBldgNm());
        dto.setAccYear(jeonse.getAccYear());
        dto.setBobn(jeonse.getBobn());
        dto.setBeforeGrntyAmount(jeonse.getBeforeGrntyAmount());
        dto.setLandGbnNm(jeonse.getLandGbnNm());
        dto.setRentFee(jeonse.getRentFee());
        dto.setBeforeMtRentChrge(jeonse.getBeforeMtRentChrge());
        dto.setLat(jeonse.getLat());
        dto.setLng(jeonse.getLng());
        dto.setApiBuildingName(jeonse.getApiBuildingName());
        return dto;
    }
}
