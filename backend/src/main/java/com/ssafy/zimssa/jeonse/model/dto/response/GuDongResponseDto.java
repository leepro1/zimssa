package com.ssafy.zimssa.jeonse.model.dto.response;

import com.ssafy.zimssa.jeonse.model.dto.GuDong;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuDongResponseDto {

    private String gunCode;
    private String dongCode;
    private String gunName;
    private String dongName;

    public static GuDongResponseDto fromGuDong(GuDong guDong) {
        GuDongResponseDto dto = new GuDongResponseDto();
        dto.setGunCode(guDong.getSigunguCd());
        dto.setDongCode(guDong.getBjdongCd());
        dto.setGunName(guDong.getSigunguNm());
        dto.setDongName(guDong.getBjdongNm());
        return dto;
    }
}
