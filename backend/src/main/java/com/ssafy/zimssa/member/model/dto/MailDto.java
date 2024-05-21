package com.ssafy.zimssa.member.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
public class MailDto {

    private String address;
    private String title;
    private String message;

}
