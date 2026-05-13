package com.medichatbot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SymptomChecker {

    public static void checkSymptoms() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter symptom:");
        String symptom = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT d.disease_name, d.description " +
                           "FROM diseases d " +
                           "JOIN disease_symptoms ds ON d.disease_id = ds.disease_id " +
                           "JOIN symptoms s ON ds.symptom_id = s.symptom_id " +
                           "WHERE s.symptom_name = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, symptom);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String disease = rs.getString("disease_name");

                System.out.println("Possible Disease: " + disease);
                System.out.println("Description: " + rs.getString("description"));

                PrescriptionService.getMedicine(disease);
                NutritionService.getNutrition(disease);
            } else {
                System.out.println("No disease found for this symptom.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}