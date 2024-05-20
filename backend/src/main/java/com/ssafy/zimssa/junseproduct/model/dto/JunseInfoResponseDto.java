package com.ssafy.zimssa.junseproduct.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

@ToString
@Data
public class JunseInfoResponseDto {
	public JunseInfoResponseDto(String rentGrntMaxLoanLmtRate, String qscTlno, String intSprtCont,
								String maxLoanLmtAmt, String rcmdProdNm, String rcmdGrntProdDvcd,
								String grntPrmeCont, String reqTrgtCont, String qscNm, String grntDvcd,
								String exptGrfeRateCont, String guidUrl, String grntReqTrgtDvcd,
								String trtBankCont,String id) {
		this.rentGrntMaxLoanLmtRate = rentGrntMaxLoanLmtRate;
		this.qscTlno = qscTlno;
		this.intSprtCont = intSprtCont;
		this.maxLoanLmtAmt = maxLoanLmtAmt;
		this.rcmdProdNm = rcmdProdNm;
		this.rcmdGrntProdDvcd = rcmdGrntProdDvcd;
		this.grntPrmeCont = grntPrmeCont;
		this.reqTrgtCont = reqTrgtCont;
		this.qscNm = qscNm;
		this.grntDvcd = grntDvcd;
		this.exptGrfeRateCont = exptGrfeRateCont;
		this.guidUrl = guidUrl;
		this.grntReqTrgtDvcd = grntReqTrgtDvcd;
		this.trtBankCont = trtBankCont;
		this.id = id;
	}

	private String rentGrntMaxLoanLmtRate;
	private String qscTlno;
	private String intSprtCont;
	private String maxLoanLmtAmt;
	private String rcmdProdNm;
	private String rcmdGrntProdDvcd;
	private String grntPrmeCont;
	private String reqTrgtCont;
	private String qscNm;
	private String grntDvcd;
	private String exptGrfeRateCont;
	private String guidUrl;
	private String grntReqTrgtDvcd;
	private String trtBankCont;
	private String id;
}
