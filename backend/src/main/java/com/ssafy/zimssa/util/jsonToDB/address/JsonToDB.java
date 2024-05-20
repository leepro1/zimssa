package com.ssafy.zimssa.util.jsonToDB.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class JsonToDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(new File(""));
            ArrayNode dataArray = (ArrayNode) root.get("DATA");

            // Connect to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query for inserting data with IGNORE to handle duplicates
            String insertSql = "INSERT IGNORE INTO address_info (sigungu_cd, sigungu_nm, bjdong_cd, bjdong_nm) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSql);

            // Insert data
            for (JsonNode item : dataArray) {
                String sigungu_cd = item.has("sigungu_cd") ? item.get("sigungu_cd").asText() : null;
                String sigungu_nm = item.has("sigungu_nm") ? item.get("sigungu_nm").asText() : null;
                String bjdong_cd = item.has("bjdong_cd") ? item.get("bjdong_cd").asText() : null;
                String bjdong_nm = item.has("bjdong_nm") ? item.get("bjdong_nm").asText() : null;

                // Set parameters
                insertStatement.setString(1, sigungu_cd);
                insertStatement.setString(2, sigungu_nm);
                insertStatement.setString(3, bjdong_cd);
                insertStatement.setString(4, bjdong_nm);

                // Add to batch
                insertStatement.addBatch();
            }

            // Execute the batch
            insertStatement.executeBatch();

            // Close the connections
            insertStatement.close();
            connection.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
