package com.medichatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupPage extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField nameField, emailField, ageField, genderField;
    JPasswordField passwordField;
    JButton signupButton, loginButton;

    public SignupPage() {

        setTitle("MediChatbot Signup");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("MediChatbot Signup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        // Age
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Age:"), gbc);

        gbc.gridx = 1;
        ageField = new JTextField(20);
        formPanel.add(ageField, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Gender:"), gbc);

        gbc.gridx = 1;
        genderField = new JTextField(20);
        formPanel.add(genderField, gbc);

        // Signup Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;

        signupButton = new JButton("Signup");
        signupButton.setPreferredSize(new Dimension(200,45));
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));

        formPanel.add(signupButton, gbc);

        // Login Button
        gbc.gridy = 6;

        loginButton = new JButton("Already have account? Login");
        loginButton.setPreferredSize(new Dimension(180,35));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 12));

        formPanel.add(loginButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Signup Logic
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DBConnection.getConnection();

                    String query = "INSERT INTO users(name,email,password,age,gender) VALUES(?,?,?,?,?)";

                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setString(1, nameField.getText());
                    ps.setString(2, emailField.getText());
                    ps.setString(3, String.valueOf(passwordField.getPassword()));
                    ps.setInt(4, Integer.parseInt(ageField.getText()));
                    ps.setString(5, genderField.getText());

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Signup Successful");

                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        // Login Navigation
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                dispose();
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}