package com.medichatbot;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    public static void sendOTP(String receiverEmail, String otp) {

         final String senderEmail = "YOUR_GMAIL@gmail.com";
        final String appPassword = "YOUR_APP_PASSWORD";

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                senderEmail,
                                appPassword
                        );
                    }
                });

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail)
            );

            message.setSubject("MediChatbot Password Reset OTP");

            message.setText("Your OTP for password recovery is: " + otp);

            Transport.send(message);

            System.out.println("OTP Email Sent Successfully!");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}