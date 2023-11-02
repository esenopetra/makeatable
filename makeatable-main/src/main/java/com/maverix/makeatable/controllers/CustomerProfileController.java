package com.maverix.makeatable.controllers;

import com.maverix.makeatable.config.Security.JwtService;
import com.maverix.makeatable.dto.User.UserGetDto;
import com.maverix.makeatable.dto.User.CustomerProfileUpdateDto;
import com.maverix.makeatable.services.CustomerProfileService;
import com.maverix.makeatable.util.JwtUtils;
import com.maverix.makeatable.util.Response;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/customer-profile")
public class CustomerProfileController {
    private final HttpServletRequest request;
    private final JwtService jwtService;
    private final JwtUtils jwtUtils;
    private final CustomerProfileService customerProfileService;

    @Autowired
    public CustomerProfileController(HttpServletRequest request, JwtService jwtService, JwtUtils jwtUtils, CustomerProfileService customerProfileService) {
        this.request = request;
        this.jwtService = jwtService;
        this.jwtUtils = jwtUtils;
        this.customerProfileService = customerProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserGetDto>> getUserProfile(@PathVariable @Positive(message = "Invalid user ID") Long id) {
        try {
            String jwtUserId = jwtService.extractId(jwtUtils.getJwtFromRequest(request));

            if (!jwtUserId.equals(String.valueOf(id))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Response.<UserGetDto>builder()
                                .timeStamp(LocalDateTime.now())
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .status(HttpStatus.FORBIDDEN)
                                .message("You are not the user associated with this account")
                                .build());
            }

            UserGetDto userGetDto = customerProfileService.getUserProfile(id);

            Response<UserGetDto> response = Response.<UserGetDto>builder()
                    .timeStamp(LocalDateTime.now())
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .data(userGetDto)
                    .build();

            return ResponseEntity.ok(response);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.<UserGetDto>builder()
                            .timeStamp(LocalDateTime.now())
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .status(HttpStatus.UNAUTHORIZED)
                            .message("JWT token has expired.")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.<UserGetDto>builder()
                            .timeStamp(LocalDateTime.now())
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("An error occurred: " + e.getMessage())
                            .build());
        }
    }

    @PreAuthorize("#email == principal.username")
    @PutMapping("/{email}")
    public ResponseEntity<Response<String>> updateUserProfile(@PathVariable @Email(message = "invalid email") String email, @RequestBody @Valid CustomerProfileUpdateDto updateDto) {
        customerProfileService.updateUserProfile(email, updateDto);

        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User profile updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }
}
