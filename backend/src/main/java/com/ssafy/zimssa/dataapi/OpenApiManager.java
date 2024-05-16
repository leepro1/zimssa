//package com.ssafy.zimssa.dataapi;
//
//import java.io.UnsupportedEncodingException;
//import java.net.http.HttpResponse;
//import java.util.Map;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//
//import lombok.extern.java.Log;
//import lombok.extern.slf4j.Slf4j;
//
//public class OpenApiManager {
//	
//
//	  private final String BASE_URL = "http://apis.data.go.kr/B551408/jnse-rcmd-info";
//	    private final String apiUri = "/jnse-rcmd-list";
//	    private final String serviceKey = "?serviceKey=wZtpHSUV%2FqDJf2Bbkyl%2FGdXH5stB5J%2Bzx3FikEoxUdeXyby3N2lIldH2Eq%2FUyXWnRIkElSObvrYvk5IFTGqj7g%3D%3D";
//	    private final String defaultQueryParam = "&MobileOS=ETC&MobileApp";
//	    							
//
//
//	    private String makeUrl () throws UnsupportedEncodingException {
//	    	
//
//	    	UserInfoDto userInfoDto = new UserInfoDto("JSON","1","1","200000000","2629000000","22","1","40000000","10000000","1","01");	    
//	    			System.out.println(userInfoDto.getMyTotDebtAmt()+">>>>>>>>>>>>>>>>>>>>>>>!!!");
//	    	
//	        return BASE_URL +
//	                apiUri 
//	                + serviceKey 
//	                +defaultQueryParam
//	                +"&dataType="+userInfoDto.getDataType()
//	                +"&numOfRows="+userInfoDto.getNumOfRows()
//	                +"&pageNo="+userInfoDto.getPageNo()
//	                +"&rentGrntAmt="+userInfoDto.getRentGrntAmt()
//	                +"&trgtLwdgCd="+userInfoDto.getTrgtLwdgCd()
//	                +"&age="+userInfoDto.getAge()
//	                +"&weddStcd="+userInfoDto.getWeddStcd()
//	                +"&myIncmAmt="+userInfoDto.getMyIncmAmt()
//	                +"&myTotDebtAmt="+userInfoDto.getMyTotDebtAmt()
//	                +"&ownHsCnt="+userInfoDto.getOwnHsCnt()
//	                +"&grntPrmeActnDvcdCont="+userInfoDto.getGrntPrmeActnDvcdCont();
//	    }
//
//	    public ResponseEntity<?> fetch() throws UnsupportedEncodingException {
////	        System.out.println(makeUrl());
////	        RestTemplate restTemplate = new RestTemplate();
////	       
////	      //  restTemplate.getMessageConverters().add(new MappingJackson2CborHttpMessageConverter());
////
////	        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
////	        
////	        ResponseEntity<Map> resultMap = restTemplate.exchange(makeUrl(), HttpMethod.GET, entity, Map.class);
////	        
////
////	        System.out.println(resultMap.getBody());
////	        System.out.println("바디"+ resultMap.getBody());
////	        return resultMap;
//	    	// //MappingJackson2HttpMessageConverter
//	        System.out.println(makeUrl());
//	        RestTemplate restTemplate = new RestTemplate();
//	       restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//	  //      restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());
//
//	        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
//	        
//	        ResponseEntity<ResponseWrapper> resultMap = restTemplate.exchange(makeUrl(), HttpMethod.GET, entity, ResponseWrapper.class);
//
//	        System.out.println("body"+ resultMap.getBody());
//	        return resultMap;
//
//	    }
//	}