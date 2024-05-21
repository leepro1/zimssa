package com.ssafy.zimssa.util.jsonToDB.subway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class JsonToDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ssafyweb";
    private static final String DB_USER = "ssafy";
    private static final String DB_PASSWORD = "ssafyssafy";

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(new File("/Users/ihuiju/realZimssa/zimssa/backend/src/main/java/com/ssafy/zimssa/util/jsonToDB/subway/subway.json"));
            ArrayNode dataArray = (ArrayNode) root;

            // Connect to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String insertSql = "INSERT INTO facilities (type, name, lat, lng) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSql);

            // Insert data
            for (JsonNode item : dataArray) {
                String name = item.has("name") ? item.get("name").asText() : null;
                String lat = item.has("lat") ? item.get("lat").asText() : null;
                String lng = item.has("lng") ? item.get("lng").asText() : null;

                // Set parameters
                insertStatement.setString(1, "subway");
                insertStatement.setString(2, name);
                insertStatement.setString(3, lat);
                insertStatement.setString(4, lng);

                // Add to batch
                insertStatement.addBatch();
            }

            // Execute the batch
            insertStatement.executeBatch();

            // Close the connections
            insertStatement.close();
            connection.close();

            System.out.println("Data inserted successfully!");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
