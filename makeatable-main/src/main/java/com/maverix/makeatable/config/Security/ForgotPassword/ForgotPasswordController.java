package com.maverix.makeatable.config.Security.ForgotPassword;
import com.maverix.makeatable.dto.User.ForgotPasswordRequestDto;
import com.maverix.makeatable.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
    @Value("${base.url}")
    private String baseUrl;
    private final UserService userService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public ForgotPasswordController(UserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    @PostMapping
    public ResponseEntity<String> requestResetPassword(@RequestBody ForgotPasswordRequestDto requestDTO) {
        String email = requestDTO.getEmail();
        String resetToken = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(email, resetToken);
        String resetLink = baseUrl + "reset-password?token=" + resetToken;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Click the link below to reset your password:\n" + resetLink);
        javaMailSender.send(message);
        return ResponseEntity.ok("Password reset link sent to your email.");
    }
}
