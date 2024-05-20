package com.ssafy.zimssa.util.jsonToDB.maemae;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class JsonToDB {

    public static void main(String[] args) {
        // JSON 파일 경로
        String jsonFilePath = "";

        // MySQL 연결 정보
        String url = "jdbc:mysql://localhost:3306/";
        String user = "";
        String password = "";

        // JDBC 연결 객체 선언
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // JSON 파일 읽기
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));
            JsonNode dataArray = rootNode.get("DATA");

            // MySQL 연결
            connection = DriverManager.getConnection(url, user, password);

            // SQL 쿼리
            String sql = "INSERT INTO maemae_info (sgg_cd, build_year, bldg_area, bjdong_cd, sgg_nm, land_gbn, bubeon, cntl_ymd, bjdong_nm, right_gbn, obj_amt, rdealer_lawdnm, bldg_nm, house_type, floor, acc_year, land_gbn_nm, tot_area, req_gbn, bonbeon, deal_ymd, lat, lng, api_building_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);

            // 데이터 삽입
            for (JsonNode item : dataArray) {
                preparedStatement.setString(1, item.get("sgg_cd").asText());
                preparedStatement.setString(2, item.get("build_year").asText());
                preparedStatement.setFloat(3, (float) item.get("bldg_area").asDouble());
                preparedStatement.setString(4, item.get("bjdong_cd").asText());
                preparedStatement.setString(5, item.get("sgg_nm").asText());
                preparedStatement.setString(6, item.get("land_gbn").asText());
                preparedStatement.setString(7, item.get("bubeon").asText());
                preparedStatement.setString(8, item.get("cntl_ymd").isNull() ? null : item.get("cntl_ymd").asText());
                preparedStatement.setString(9, item.get("bjdong_nm").asText());
                preparedStatement.setString(10, item.get("right_gbn").isNull() ? null : item.get("right_gbn").asText());
                preparedStatement.setString(11, item.get("obj_amt").asText());
                preparedStatement.setString(12, item.get("rdealer_lawdnm").asText());
                preparedStatement.setString(13, item.get("bldg_nm").asText());
                preparedStatement.setString(14, item.get("house_type").asText());
                preparedStatement.setInt(15, item.get("floor").asInt());
                preparedStatement.setString(16, item.get("acc_year").asText());
                preparedStatement.setString(17, item.get("land_gbn_nm").asText());
                preparedStatement.setFloat(18, (float) item.get("tot_area").asDouble());
                preparedStatement.setString(19, item.get("req_gbn").asText());
                preparedStatement.setString(20, item.get("bonbeon").asText());
                preparedStatement.setString(21, item.get("deal_ymd").asText());
                preparedStatement.setString(22, item.has("lat") ? item.get("lat").asText() : null);
                preparedStatement.setString(23, item.has("lng") ? item.get("lng").asText() : null);
                preparedStatement.setString(24, item.has("api_building_name") ? item.get("api_building_name").asText() : null);

                preparedStatement.addBatch();
            }

            // 일괄 실행
            preparedStatement.executeBatch();
            System.out.println("Data insertion completed!");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 리소스 정리
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
