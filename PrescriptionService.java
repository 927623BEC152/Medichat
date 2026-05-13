package com.medichatbot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrescriptionService {

    public static void getMedicine(String diseaseName) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT m.medicine_name, m.dosage " +
                           "FROM medicines m " +
                           "JOIN prescriptions p ON m.medicine_id = p.medicine_id " +
                           "JOIN diseases d ON p.disease_id = d.disease_id " +
                           "WHERE d.disease_name = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, diseaseName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Recommended Medicine: " + rs.getString("medicine_name"));
                System.out.println("Dosage: " + rs.getString("dosage"));
            } else {
                System.out.println("No medicine found.");
            }

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}