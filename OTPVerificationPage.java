package com.medichatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OTPVerificationPage extends JFrame {

    JTextField otpField;
    JPasswordField newPasswordField;
    JButton verifyButton;

    String userEmail;
    String generatedOTP;

    public OTPVerificationPage(String email, String otp) {

        this.userEmail = email;
        this.generatedOTP = otp;

        setTitle("OTP Verification");
        setSize(500,400);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx=0;
        gbc.gridy=0;
        add(new JLabel("Enter OTP:"), gbc);

        gbc.gridx=1;
        otpField = new JTextField(20);
        add(otpField, gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        add(new JLabel("New Password:"), gbc);

        gbc.gridx=1;
        newPasswordField = new JPasswordField(20);
        add(newPasswordField, gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=2;

        verifyButton = new JButton("Verify & Reset Password");
        add(verifyButton, gbc);

        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String enteredOTP = otpField.getText();

                if(enteredOTP.equals(generatedOTP)) {

                    ResetPassword.resetPassword(
                            userEmail,
                            String.valueOf(newPasswordField.getPassword())
                    );

                    JOptionPane.showMessageDialog(
                            null,
                            "Password Reset Successful"
                    );

                    new LoginPage();
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid OTP"
                    );
                }
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}