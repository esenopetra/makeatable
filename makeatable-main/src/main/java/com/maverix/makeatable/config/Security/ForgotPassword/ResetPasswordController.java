package com.maverix.makeatable.config.Security.ForgotPassword;

import com.maverix.makeatable.dto.User.ResetPasswordRequestDTO;
import com.maverix.makeatable.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public ResetPasswordController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<String> handleResetPasswordLink(@RequestParam("token") String token) {
        String email = userService.getEmailByPasswordResetToken(token);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }

        // Provide a form for the user to enter a new password
        // In your frontend, redirect the user to a password reset form

        return ResponseEntity.ok("Enter your new password.");
    }
    @PostMapping
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordRequestDTO requestDTO) {

        String email = userService.getEmailByPasswordResetToken(token);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }


        userService.updatePassword(email, passwordEncoder.encode(requestDTO.getNewPassword()));

        return ResponseEntity.ok("Password reset successfully.");
    }
}
