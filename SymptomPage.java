package com.medichatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SymptomPage extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField symptomField;
    JTextArea resultArea;
    JButton checkButton;
    JButton logoutButton;

    int userId;

    public SymptomPage(String username, int userId) {

        this.userId = userId;

        setTitle("MediChatbot - Symptom Checker");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(true);

        // Disclaimer
        int choice = JOptionPane.showConfirmDialog(
                null,
                "Disclaimer:\n\n" +
                "This application provides only basic medical suggestions.\n" +
                "It should not be used for:\n" +
                "- Children under 5 years\n" +
                "- Pregnant women\n" +
                "- Allergy-prone individuals\n" +
                "- Serious medical emergencies\n\n" +
                "Please consult a doctor for proper treatment.\n\n" +
                "Do you agree to continue?",
                "Medical Disclaimer",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if(choice != JOptionPane.YES_OPTION) {
            new LoginPage();
            dispose();
            return;
        }

        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome " + username + " to MediChatbot");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        topPanel.add(welcomeLabel);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15,15,15,15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Symptom label
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Enter Symptom:"), gbc);

        // Symptom field
        gbc.gridx = 1;
        symptomField = new JTextField(20);
        centerPanel.add(symptomField, gbc);

        // Check button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        checkButton = new JButton("Check Symptoms");
        checkButton.setPreferredSize(new Dimension(220,45));
        checkButton.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(checkButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Result Panel
        JPanel resultPanel = new JPanel(new BorderLayout());

        JLabel resultLabel = new JLabel("Medical Results");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Arial", Font.PLAIN, 16));
        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        add(resultPanel, BorderLayout.EAST);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();

        logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(150,40));

        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Check logic
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String symptom = symptomField.getText();

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

                    if(rs.next()) {

                        String disease = rs.getString("disease_name");
                        String description = rs.getString("description");

                        String result = "Disease: " + disease + "\n\n";
                        result += "Description: " + description + "\n\n";

                        // Medicine
                        String medQuery = "SELECT m.medicine_name, m.dosage " +
                                "FROM medicines m " +
                                "JOIN prescriptions p ON m.medicine_id = p.medicine_id " +
                                "JOIN diseases d ON p.disease_id = d.disease_id " +
                                "WHERE d.disease_name=?";

                        PreparedStatement medPs = con.prepareStatement(medQuery);
                        medPs.setString(1, disease);

                        ResultSet medRs = medPs.executeQuery();

                        if(medRs.next()) {
                            result += "Medicine: " + medRs.getString("medicine_name") + "\n";
                            result += "Dosage: " + medRs.getString("dosage") + "\n\n";
                        }

                        // Nutrition
                        String nutritionQuery = "SELECT n.diet_plan, n.water_intake " +
                                "FROM nutrition n " +
                                "JOIN diseases d ON n.disease_id = d.disease_id " +
                                "WHERE d.disease_name=?";

                        PreparedStatement nutPs = con.prepareStatement(nutritionQuery);
                        nutPs.setString(1, disease);

                        ResultSet nutRs = nutPs.executeQuery();

                        if(nutRs.next()) {
                            result += "Diet Plan: " + nutRs.getString("diet_plan") + "\n";
                            result += "Water Intake: " + nutRs.getString("water_intake");
                        }

                        resultArea.setText(result);

                        // Save history
                        String saveQuery = "INSERT INTO consultation_history(user_id,symptom,diagnosis) VALUES(?,?,?)";

                        PreparedStatement savePs = con.prepareStatement(saveQuery);
                        savePs.setInt(1, userId);
                        savePs.setString(2, symptom);
                        savePs.setString(3, disease);

                        savePs.executeUpdate();

                    } else {
                        resultArea.setText("No disease found.");
                    }

                } catch(Exception ex) {
                    resultArea.setText(ex.toString());
                }
            }
        });

        // Logout
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Logged out successfully");
                new LoginPage();
                dispose();
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}