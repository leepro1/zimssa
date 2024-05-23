package com.ssafy.zimssa.news.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/news")
@Tag(name = "뉴스 컨트롤러", description = "뉴스 정보를 제공하는 컨트롤러")
@Slf4j
public class NewsController {

	
    private static final String API_URL = "https://openapi.naver.com/v1/search/news.json?display=5&query=";
    private static final String CLIENT_ID = "yaHcxXZeuNbl9vz_av53";
    private static final String CLIENT_SECRET = "WsXbOngZir";
	
    @Operation(summary = "네이버 API에서 뉴스 정보 전달", description = "뉴스 정보 제공")
    @GetMapping(value="/{keyword}", produces = "application/json;charset=utf8")
    public ResponseEntity<?> getNews(@PathVariable("keyword") String keyword) {
        log.info("getNews");
        String jsonResponse, resultJson="";

        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(API_URL + keyword);
            request.addHeader("X-Naver-Client-Id", CLIENT_ID);
            request.addHeader("X-Naver-Client-Secret", CLIENT_SECRET);

            HttpResponse response = null;
            response = client.execute(request);
            jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("Response: " + jsonResponse);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // target.data 내부의 데이터 추출
            JsonNode targetData = rootNode.path("items");
            if (targetData.isArray()) {
                ArrayNode resultArray = objectMapper.createArrayNode();
                int limit = Math.min(10, targetData.size());
                for (int i = 0; i < limit; i++) {
                    resultArray.add(targetData.get(i));
                }

                // 결과 JSON 생성
                resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultArray);
                System.out.println("Extracted Data: " + resultJson);
            } else {
                System.out.println("target.data is not an array.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<String>(resultJson, HttpStatus.CREATED);
    }
}