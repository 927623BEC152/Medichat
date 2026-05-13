package com.medichatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField nameField;
    JPasswordField passwordField;
    JButton loginButton, signupButton;
    JButton forgotButton;

    public LoginPage() {

        setTitle("MediChatbot Login");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(true);

        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("MediChatbot Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        // Name Field
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        // Password Field
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

     // Login Button (bigger)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 45));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));

        formPanel.add(loginButton, gbc);

        // Signup Button (smaller)
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        signupButton = new JButton("New User? Go to Signup");
        signupButton.setPreferredSize(new Dimension(180, 35));
        signupButton.setFont(new Font("Arial", Font.PLAIN, 12));

        formPanel.add(signupButton, gbc);

        add(formPanel, BorderLayout.CENTER);
        gbc.gridy = 4;

        forgotButton = new JButton("Forgot Password?");
        forgotButton.setPreferredSize(new Dimension(180,35));
        forgotButton.setFont(new Font("Arial", Font.PLAIN, 12));

        formPanel.add(forgotButton, gbc);
        
        // Login Logic
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DBConnection.getConnection();

                    String query = "SELECT * FROM users WHERE name=? AND password=?";

                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, nameField.getText());
                    ps.setString(2, String.valueOf(passwordField.getPassword()));

                    ResultSet rs = ps.executeQuery();

                    if(rs.next()) {
                        int userId = rs.getInt("user_id");

                        JOptionPane.showMessageDialog(null, "Login Successful");

                        new SymptomPage(nameField.getText(), userId);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Login Credentials");
                    }

                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        // Signup Navigation
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignupPage();
                dispose();
            }
        });
        
        forgotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ForgotPasswordPage();
                dispose();
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}