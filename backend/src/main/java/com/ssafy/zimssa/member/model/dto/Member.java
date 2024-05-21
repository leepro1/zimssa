package com.ssafy.zimssa.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
public class Member implements Serializable{

	@NotBlank // (1)
	@Length(min = 3, max = 20) // (2)
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$") // (3)
	private String id;

	@NotBlank
	@Length(min = 1, max = 50)
	private String password;

	@NotBlank
	private String name;

	@NotBlank
	private String emailId;

	@NotBlank
	private String emailDomain;

	private String joinDate;
	
	private String refreshToken;
	
	private String role;
	

}
