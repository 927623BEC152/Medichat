package com.medichatbot;

import java.sql.*;

public class ResetPassword {

    public static void resetPassword(
            String email,
            String newPassword
    ) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE users SET password=? WHERE email=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, newPassword);
            ps.setString(2, email);

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}