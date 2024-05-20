package com.ssafy.zimssa.house.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordRequestDto {

    private TradeType tradeType;
    private String keyword;

}
