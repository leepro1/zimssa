package com.ssafy.zimssa.junseproduct.controller;

import com.ssafy.zimssa.junseproduct.apiresponse.ApiResponse;
import com.ssafy.zimssa.junseproduct.model.dto.JunseInfoResponseDto;
import com.ssafy.zimssa.junseproduct.apiresponse.JunseProductApiResponse;
import com.ssafy.zimssa.junseproduct.model.dto.JunseProductDto;
import com.ssafy.zimssa.junseproduct.model.dto.UserInfoDto;
import com.ssafy.zimssa.junseproduct.model.service.JunseService;
import java.io.UnsupportedEncodingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class JunseController {
	

	  private final RestTemplate restTemplate = new RestTemplate();
	    private final ObjectMapper objectMapper = new ObjectMapper();

        private final JunseService junseService;

    public JunseController(JunseService junseService) {
        this.junseService = junseService;
    }

    private final String BASE_URL = "http://apis.data.go.kr/B551408/jnse-rcmd-info";
	    private final String apiUri = "/jnse-rcmd-list";
	    private final String serviceKey = "?serviceKey=sEzAWExEPqyBjnP5C/tf28HWGYMkG1SfSGQb6mXqcZZVkYY0krdnzRZtaUqoqF6vrJwHPPaNRWmsYtigkd4v8A==";
        private final String DETAILINFO_URL= "/jnse-prod-dtl-info";


    @PostMapping("/junse")
    public ResponseEntity<JunseInfoResponseDto> fetchDataFromAPI(@RequestBody UserInfoDto userInfoDto) throws Exception {
    	
        // API 엔드포인트 및 파라미터 설정
    	String url = makeUrl(userInfoDto);

        // API 호출 및 데이터 수신
        System.out.println(url);
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("!@@@response  "+ response);

        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse apiResponse = objectMapper.readValue(response, ApiResponse.class);

        // grantDvcd는 조건에 맞는 상품코드( 가장 우선순위가 높음 rank 1)
        String grantDvcd = null;
        if (apiResponse != null && apiResponse.getBody() != null && apiResponse.getBody().getItems() != null) {
            for (ApiResponse.Item item : apiResponse.getBody().getItems()) {
                grantDvcd = item.getGrntDvcd();
                break;
            }
        }
        System.out.println("GrantDvcd: " + grantDvcd);


        // 나온 상품코드를 바탕으로 상세내용 조회

        JunseInfoResponseDto junseInfoResponseDto = null;
        url= BASE_URL+DETAILINFO_URL+serviceKey+"&dataType=JSON&grntDvcd="+grantDvcd;
        System.out.println(url);
        response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
        JunseProductApiResponse junseProductApiResponse = objectMapper.readValue(response, JunseProductApiResponse.class);

        if (junseProductApiResponse != null && junseProductApiResponse.getBody() != null && junseProductApiResponse.getBody().getItem() != null) {
            JunseProductDto item = junseProductApiResponse.getBody().getItem();

            System.out.println("rentGrntMaxLoanLmtRate: " + item.getRentGrntMaxLoanLmtRate());
            System.out.println("qscTlno: " + item.getQscTlno());
            System.out.println("intSprtCont: " + item.getIntSprtCont());
            System.out.println("maxLoanLmtAmt: " + item.getMaxLoanLmtAmt());
            System.out.println("rcmdProdNm: " + item.getRcmdProdNm());
            System.out.println("rcmdGrntProdDvcd: " + item.getRcmdGrntProdDvcd());
            System.out.println("grntPrmeCont: " + item.getGrntPrmeCont());
            System.out.println("reqTrgtCont: " + item.getReqTrgtCont());
            System.out.println("qscNm: " + item.getQscNm());
            System.out.println("grntDvcd: " + item.getGrntDvcd());
            System.out.println("exptGrfeRateCont: " + item.getExptGrfeRateCont());
            System.out.println("guidUrl: " + item.getGuidUrl());
            System.out.println("grntReqTrgtDvcd: " + item.getGrntReqTrgtDvcd());
            System.out.println("trtBankCont: " + item.getTrtBankCont());

            junseInfoResponseDto = new JunseInfoResponseDto(
                    item.getRentGrntMaxLoanLmtRate(),
                    item.getQscTlno(),
                    item.getIntSprtCont(),
                    item.getMaxLoanLmtAmt(),
                    item.getRcmdProdNm(),
                    item.getRcmdGrntProdDvcd(),
                    item.getGrntPrmeCont(),
                    item.getReqTrgtCont(),
                    item.getQscNm(),
                    item.getGrntDvcd(),
                    item.getExptGrfeRateCont(),
                    item.getGuidUrl(),
                    item.getGrntReqTrgtDvcd(),
                    item.getTrtBankCont()
            );        }
        // ResponseEntity 반환
        junseService.save(junseInfoResponseDto);
        if (junseInfoResponseDto != null) {
            return ResponseEntity.ok(junseInfoResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }

        
        
        
    }
    

    private String makeUrl (UserInfoDto userInfoDto) throws UnsupportedEncodingException {
    	

    	//UserInfoDto userInfoDto = new UserInfoDto("JSON","1","1","200000000","2629000000","22","1","40000000","10000000","1","01");


//        return BASE_URL
//        		+ apiUri
//                + serviceKey
//                 +"&dataType="+userInfoDto.getDataType()
//                +"&numOfRows="+userInfoDto.getNumOfRows()
//                +"&pageNo="+userInfoDto.getPageNo()
//                +"&rentGrntAmt="+userInfoDto.getRentGrntAmt()
//                +"&trgtLwdgCd="+userInfoDto.getTrgtLwdgCd()
//                +"&age="+userInfoDto.getAge()
//                +"&weddStcd="+userInfoDto.getWeddStcd()
//                +"&myIncmAmt="+userInfoDto.getMyIncmAmt()
//                +"&myTotDebtAmt="+userInfoDto.getMyTotDebtAmt()
//                +"&ownHsCnt="+userInfoDto.getOwnHsCnt()
//                +"&grntPrmeActnDvcdCont="+userInfoDto.getGrntPrmeActnDvcdCont();
        return BASE_URL
                + apiUri
                + serviceKey
                +"&dataType=JSON"//+userInfoDto.getDataType()
                +"&numOfRows=1"//+userInfoDto.getNumOfRows()
                +"&pageNo=1"//+userInfoDto.getPageNo()
                +"&rentGrntAmt="+userInfoDto.getRentGrntAmt()
                +"&trgtLwdgCd="+userInfoDto.getTrgtLwdgCd()
                +"&age="+userInfoDto.getAge()
                +"&weddStcd="+userInfoDto.getWeddStcd()
                +"&myIncmAmt="+userInfoDto.getMyIncmAmt()
                +"&myTotDebtAmt="+userInfoDto.getMyTotDebtAmt()
                +"&ownHsCnt="+userInfoDto.getOwnHsCnt()
                +"&grntPrmeActnDvcdCont="+userInfoDto.getGrntPrmeActnDvcdCont();
    }
}
