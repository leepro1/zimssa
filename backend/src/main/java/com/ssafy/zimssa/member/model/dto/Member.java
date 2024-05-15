package com.ssafy.zimssa.member.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member implements Serializable{

	private String id;
	private String password;
	private String name;
	private String emailId;
	private String emailDomain;
	private String joinDate;
	private String refreshToken;
	
	

}
