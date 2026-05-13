package com.medichatbot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NutritionService {

    public static void getNutrition(String diseaseName) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT n.diet_plan, n.water_intake " +
                           "FROM nutrition n " +
                           "JOIN diseases d ON n.disease_id = d.disease_id " +
                           "WHERE d.disease_name = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, diseaseName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Diet Plan: " + rs.getString("diet_plan"));
                System.out.println("Water Intake: " + rs.getString("water_intake"));
            } else {
                System.out.println("No nutrition advice found.");
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}