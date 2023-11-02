package com.maverix.makeatable.config.Mail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {


    private final MailSender mailSender;

    public MailController(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/send-mail")
    public String sendMail() {
        mailSender.sendSimpleMail();
        return "Mail sent successfully!";
    }
}
