package com.ssafy.zimssa.junseproduct.apiresponse;

import com.ssafy.zimssa.junseproduct.model.dto.JunseProductDto;
import lombok.Data;

@Data
public class JunseProductApiResponse
{
    private Header header;
    private Body body;

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    public static class Body {
        private int pageNo;
        private int totalCount;
        private int numOfRows;
        private JunseProductDto item;

        @Data
        public static class Item {
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
        }
    }
}