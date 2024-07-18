package com.compass.infraestructure.adapters;

import com.compass.application.adapters.EmailAdapter;
import com.compass.domain.user.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailAdapter {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String send(Email email, String message, String token) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("novelliasher@gmail.com");
            mailMessage.setTo(email.getValue());
            mailMessage.setSubject("Reset your password of ecommerce UOL");
            mailMessage.setText(message + "\n\nToken: " + token);

            mailSender.send(mailMessage);

            return "Email sent successfully";
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
