package com.medichatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ForgotPasswordPage extends JFrame {

    JTextField nameField,emailField;
    JButton recoverButton,backButton;

    public ForgotPasswordPage() {

        setTitle("Forgot Password");
        setSize(500,400);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx=0;
        gbc.gridy=0;
        add(new JLabel("Name:"),gbc);

        gbc.gridx=1;
        nameField = new JTextField(20);
        add(nameField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        add(new JLabel("Email:"),gbc);

        gbc.gridx=1;
        emailField = new JTextField(20);
        add(emailField,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        recoverButton = new JButton("Recover Password");
        add(recoverButton,gbc);

        gbc.gridx=1;
        backButton = new JButton("Back");
        add(backButton,gbc);

        recoverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DBConnection.getConnection();

                    String query = "SELECT * FROM users WHERE name=? AND email=?";

                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setString(1, nameField.getText());
                    ps.setString(2, emailField.getText());

                    ResultSet rs = ps.executeQuery();

                    if(rs.next()) {

                        String otp = String.valueOf(
                                (int)(Math.random() * 900000) + 100000
                        );

                        EmailSender.sendOTP(
                                emailField.getText(),
                                otp
                        );

                        JOptionPane.showMessageDialog(
                                null,
                                "OTP sent to your email"
                        );

                        new OTPVerificationPage(
                                emailField.getText(),
                                otp
                        );

                        dispose();

                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "User not found"
                        );
                    }

                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                dispose();
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}