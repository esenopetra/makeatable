package com.maverix.makeatable.config.Mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailSender {

    @Value("${base.url}")
    private String baseUrl;
    private final JavaMailSender javaMailSender;

    public MailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("erfanhabeebvn@gmail.com");
        message.setSubject("Hello");
        message.setText("Hello, this is a test email.");
        javaMailSender.send(message);
    }
    public void sendVerificationEmail(String toEmail, String verificationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify your email");
        message.setText("Thank you for registering. Please click the link below to verify your email:\n"
                + baseUrl+"verify?token=" + verificationToken);
        javaMailSender.send(message);
    }
}
