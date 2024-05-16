package com.ssafy.zimssa.dataapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserInfoDto {

	public String dataType; 
	public String numOfRows;
	public String pageNo;
	public String rentGrntAmt; // 임차보증금액
	public String trgtLwdgCd; // 법정동코드
	public String age; // 만 나이
	public String weddStcd; //  결혼 구분 (1: 미혼 2: 기혼 3: 신혼 4: 결혼예정)
	public String myIncmAmt ; // 연 소득금액 (원)
	public String myTotDebtAmt	; //총 부채금액 (원)
	public String ownHsCnt; // 주택보유수
	public String grntPrmeActnDvcdCont; //보증 우대 조치 구분 (01: 2자녀이상 02: 다문화가정 03: 한부모가구 등)
	public UserInfoDto(String dataType, String numOfRows, String pageNo, String rentGrntAmt, String trgtLwdgCd,
			String age, String weddStcd, String myIncmAmt, String myTotDebtAmt, String ownHsCnt,
			String grntPrmeActnDvcdCont) {
		super();
		this.dataType = dataType;
		this.numOfRows = numOfRows;
		this.pageNo = pageNo;
		this.rentGrntAmt = rentGrntAmt;
		this.trgtLwdgCd = trgtLwdgCd;
		this.age = age;
		this.weddStcd = weddStcd;
		this.myIncmAmt = myIncmAmt;
		this.myTotDebtAmt = myTotDebtAmt;
		this.ownHsCnt = ownHsCnt;
		this.grntPrmeActnDvcdCont = grntPrmeActnDvcdCont;
	}

	
}
