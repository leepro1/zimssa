package com.ssafy.zimssa.util.jsonToDB.jeonse;

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
            String sql = "INSERT INTO jeonse_info VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

// 데이터 삽입
            for (JsonNode item : dataArray) {
                preparedStatement.setString(1, item.has("house_gbn_nm") ? item.get("house_gbn_nm").asText() : null);
                if (item.has("rent_area") && !item.get("rent_area").isNull()) {
                    preparedStatement.setFloat(2, (float) item.get("rent_area").asDouble());
                } else {
                    preparedStatement.setNull(2, Types.FLOAT);
                }
                preparedStatement.setString(3, item.has("sgg_cd") ? item.get("sgg_cd").asText() : null);
                preparedStatement.setString(4, item.has("build_year") ? item.get("build_year").asText() : null);
                preparedStatement.setString(5, item.has("rent_gtn") ? item.get("rent_gtn").asText() : null);
                preparedStatement.setString(6, item.has("bjdong_cd") ? item.get("bjdong_cd").asText() : null);
                preparedStatement.setString(7, item.has("rent_gbn") ? item.get("rent_gbn").asText() : null);
                preparedStatement.setString(8, item.has("cntrct_updt_rqest_at") ? item.get("cntrct_updt_rqest_at").asText() : null);
                if (item.has("flr_no") && !item.get("flr_no").isNull()) {
                    preparedStatement.setInt(9, item.get("flr_no").asInt());
                } else {
                    preparedStatement.setNull(9, Types.INTEGER);
                }
                preparedStatement.setString(10, item.has("sgg_nm") ? item.get("sgg_nm").asText() : null);
                preparedStatement.setString(11, item.has("land_gbn") ? item.get("land_gbn").asText() : null);
                preparedStatement.setString(12, item.has("cntrct_de") ? item.get("cntrct_de").asText() : null);
                preparedStatement.setString(13, item.has("new_ron_secd") ? item.get("new_ron_secd").asText() : null);
                preparedStatement.setString(14, item.has("bjdong_nm") ? item.get("bjdong_nm").asText() : null);
                preparedStatement.setString(15, item.has("cntrct_prd") ? item.get("cntrct_prd").asText() : null);
                preparedStatement.setString(16, item.has("bubn") ? item.get("bubn").asText() : null);
                preparedStatement.setString(17, item.has("bldg_nm") ? item.get("bldg_nm").asText() : null);
                preparedStatement.setString(18, item.has("acc_year") ? item.get("acc_year").asText() : null);
                preparedStatement.setString(19, item.has("bobn") ? item.get("bobn").asText() : null);
                preparedStatement.setString(20, item.has("before_grnty_amount") ? item.get("before_grnty_amount").asText() : null);
                preparedStatement.setString(21, item.has("land_gbn_nm") ? item.get("land_gbn_nm").asText() : null);
                preparedStatement.setString(22, item.has("rent_fee") ? item.get("rent_fee").asText() : null);
                preparedStatement.setString(23, item.has("before_mt_rent_chrge") ? item.get("before_mt_rent_chrge").asText() : null);
                preparedStatement.setString(24, item.has("lat") ? item.get("lat").asText() : null);
                preparedStatement.setString(25, item.has("lng") ? item.get("lng").asText() : null);
                preparedStatement.setString(26, item.has("api_building_name") ? item.get("api_building_name").asText() : null);

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
