//package com.ssafy.zimssa.dataapi;
//
//import java.io.UnsupportedEncodingException;
//
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//
//@RestController
//@Slf4j
//
//public class OpenApiController {
//    private final OpenApiManager openApiManager = new OpenApiManager();
//
//
//    @GetMapping("open-api")
//    public ResponseEntity<?> fetch() throws UnsupportedEncodingException {
//    	
//    	ResponseEntity<ResponseWrapper> responseEntity = openApiManager.fetch();
//        System.out.println("API 호출 결과: " + responseEntity);
//
//    	   ResponseWrapper responseBody = openApiManager.fetch().getBody();
//           System.out.println(responseBody);
//           return ResponseEntity.ok(responseBody); // 성공응답 반환
//    }
////    
////    @GetMapping("/open-api")
////    public ResponseEntity<?> fetch( ) throws UnsupportedEncodingException {
////        log.info("공공데이터포털 API 호출 중...");
////     //   RestTemplate restTemplate = new RestTemplate();
////
////
////        ResponseEntity<?> response = openApiManager.fetch(); // OpenApiManager에서 API 호출하여 응답 받기
////        System.out.println(response+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////        //response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
////        return null;
////    }
//    
//
//}
