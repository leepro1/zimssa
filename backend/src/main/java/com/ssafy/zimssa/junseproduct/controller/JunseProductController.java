package com.ssafy.zimssa.junseproduct.controller;

import com.ssafy.zimssa.junseproduct.apiresponse.ApiResponse;
import com.ssafy.zimssa.junseproduct.model.dto.JunseInfoResponseDto;
import com.ssafy.zimssa.junseproduct.apiresponse.JunseProductApiResponse;
import com.ssafy.zimssa.junseproduct.model.dto.JunseProductDto;
import com.ssafy.zimssa.junseproduct.model.dto.JunseUserInfoDto;
import com.ssafy.zimssa.junseproduct.model.service.JunseService;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/junseproduct")
public class JunseProductController {
	

	  private final RestTemplate restTemplate = new RestTemplate();
	    private final ObjectMapper objectMapper = new ObjectMapper();

        private final JunseService junseService;

    public JunseProductController(JunseService junseService) {
        this.junseService = junseService;
    }

    private final String BASE_URL = "http://apis.data.go.kr/B551408/jnse-rcmd-info";
	    private final String apiUri = "/jnse-rcmd-list";
	    private final String serviceKey = "?serviceKey=sEzAWExEPqyBjnP5C/tf28HWGYMkG1SfSGQb6mXqcZZVkYY0krdnzRZtaUqoqF6vrJwHPPaNRWmsYtigkd4v8A==";
        private final String DETAILINFO_URL= "/jnse-prod-dtl-info";


    @GetMapping("/junse/list/{userId}")
    public ResponseEntity<JunseInfoResponseDto> list(@PathVariable("userId") String userId) throws Exception {
        System.out.println("list get>>>>>>>>>>>>>>>>>");
        System.out.println(userId);

        JunseInfoResponseDto junseInfoResponseDto = junseService.getproduct(userId);
        //return new ResponseEntity<>(junseInfoResponseDto, HttpStatus.OK);
        if (junseInfoResponseDto != null) {
            // 클라이언트에게 HTTP 상태 코드 200(OK)와 함께 응답 본문을 전송합니다.
            System.out.println("성공");
            System.out.println(junseInfoResponseDto);
            return new ResponseEntity<JunseInfoResponseDto>(junseInfoResponseDto, HttpStatus.OK);
        } else {
            // 클라이언트에게 HTTP 상태 코드 404(NOT FOUND)와 함께 응답을 전송합니다.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/junse/{userId}")
    public ResponseEntity<JunseInfoResponseDto> fetchDataFromAPI(@PathVariable String userId, @RequestBody JunseUserInfoDto userInfoDto) throws Exception {
        // 메서드 본문 내용은 변경하지 않고 사용자의 아이디를 추가로 받아서 처리합니다.
        // API 엔드포인트 및 파라미터 설정
        System.out.println(userInfoDto);
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
                    item.getTrtBankCont(),
                    userId
            );        }
        // ResponseEntity 반환
        System.out.println("!!!!!!");
        System.out.println(junseInfoResponseDto);
        junseService.save(junseInfoResponseDto);
        if (junseInfoResponseDto != null) {
            return ResponseEntity.ok(junseInfoResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }

        
        
        
    }
//    @GetMapping("/list/{userId")
//    public ResponseEntity<String > list(@PathVariable String userId)
//    {
//
//    }
//

    private String makeUrl (JunseUserInfoDto userInfoDto) throws UnsupportedEncodingException {
    	

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
