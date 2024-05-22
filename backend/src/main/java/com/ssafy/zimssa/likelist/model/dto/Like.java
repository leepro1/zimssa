package com.ssafy.zimssa.likelist.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Data
public class Like {

    int id;

    String type; //maemae, jeonse
    String location;
    String house_name;
    String user_id;
}
