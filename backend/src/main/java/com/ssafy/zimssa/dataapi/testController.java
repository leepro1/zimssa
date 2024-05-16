package com.ssafy.zimssa.dataapi;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class testController {
	

	  private final RestTemplate restTemplate = new RestTemplate();
	    private final ObjectMapper objectMapper = new ObjectMapper();


	  private final String BASE_URL = "http://apis.data.go.kr/B551408/jnse-rcmd-info";
	    private final String apiUri = "/jnse-rcmd-list";
	    private final String serviceKey = "?serviceKey=sEzAWExEPqyBjnP5C%2Ftf28HWGYMkG1SfSGQb6mXqcZZVkYY0krdnzRZtaUqoqF6vrJwHPPaNRWmsYtigkd4v8A%3D%3D";
	    							


    @GetMapping("/fetchData")
    public ApiResponse fetchDataFromAPI() throws UnsupportedEncodingException {
    	
        // API 엔드포인트 및 파라미터 설정
    	String url = makeUrl();

        // API 호출 및 데이터 수신
        System.out.println(url);
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("!@@@response  "+ response);
        //ApiResponse r = objectMapper.readValue(response, ApiResponse.class);
		return null;

        
        
        
    }
    

    private String makeUrl () throws UnsupportedEncodingException {
    	

    	UserInfoDto userInfoDto = new UserInfoDto("JSON","1","1","200000000","2629000000","22","1","40000000","10000000","1","01");	    

    	
        return BASE_URL 
        		+ apiUri 
                + serviceKey 
                 +"&dataType="+userInfoDto.getDataType()
                +"&numOfRows="+userInfoDto.getNumOfRows()
                +"&pageNo="+userInfoDto.getPageNo()
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
