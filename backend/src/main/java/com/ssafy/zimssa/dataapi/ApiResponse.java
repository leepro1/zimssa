package com.ssafy.zimssa.dataapi;

import lombok.Data;

@Data
public class ApiResponse {
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
	        private Item[] items;
	    }

	    @Data
	    public static class Item {
	        private int rcmdProrRnk;
	        private String grntDvcd;
	        private String grntLmtAmt;
	        private String loanLmtAmt;
	    }
}
