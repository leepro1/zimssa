package com.ssafy.zimssa.junseproduct.model.service;

import com.ssafy.zimssa.junseproduct.model.dto.JunseInfoResponseDto;

public interface JunseService {
    void save(JunseInfoResponseDto junseInfoResponseDto) throws Exception;

    JunseInfoResponseDto getproduct(String userId) throws Exception;

}
