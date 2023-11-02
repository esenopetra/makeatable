package com.maverix.makeatable.config.Security;

import com.maverix.makeatable.config.Security.Dto.AuthenticationRequest;
import com.maverix.makeatable.config.Security.Dto.AuthenticationResponse;
import com.maverix.makeatable.config.Security.Dto.ExtractEmailDto;
import com.maverix.makeatable.config.Security.Dto.RegisterRequest;
import com.maverix.makeatable.dto.Orders.OrdersGetDto;
import com.maverix.makeatable.dto.User.UserRegistrationDto;
import com.maverix.makeatable.enums.UserStatus;
import com.maverix.makeatable.exceptions.DuplicateEmailException;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.UserRepository;
import com.maverix.makeatable.util.JwtUtils;
import com.maverix.makeatable.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    public User registerUser(UserRegistrationDto registrationDto) {
        try {
            User user = new User();
            user.setFullName(registrationDto.getFullName());
            user.setEmail(registrationDto.getEmail());
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            user.setMobileNum(registrationDto.getMobileNumber());
            user.setPreference(registrationDto.getPreference());
            user.setUserType(registrationDto.getUserType());

            user.setUserStatus(UserStatus.PENDING);
            user.setUpdatedAt(LocalDateTime.now());
            user.setCreatedAt(LocalDateTime.now());

            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exists: " + registrationDto.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during registration.", e);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Boolean validateToken(String jwtFromRequest) {
      return  jwtService.isTokenExpired(jwtFromRequest);
    }

    public ResponseEntity<Response<Long>> getIdFromToken(HttpServletRequest request) {
        String jwtUserId = jwtService.extractId(jwtUtils.getJwtFromRequest(request));
        Response<Long> response = Response.<Long>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Id Retreived")
                .data(Long.valueOf(jwtUserId))
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response<String>> getEmailFromToken(HttpServletRequest request) {
        String jwtUserEmail = jwtService.extractUsername(jwtUtils.getJwtFromRequest(request));
        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Email Retreived")
                .data(jwtUserEmail)
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response<String>> getEmailFromTokenUsingBody(ExtractEmailDto extractEmailDto) {
        String jwtUserEmail = jwtService.extractUsername(extractEmailDto.token);
        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Email Retreived")
                .data(jwtUserEmail)
                .build();
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<Response<Long>> getIdFromTokenUsingBody(ExtractEmailDto extractEmailDto) {
        String jwtUserId = jwtService.extractId(extractEmailDto.token);
        Response<Long> response = Response.<Long>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Id Retreived")
                .data(Long.valueOf(jwtUserId))
                .build();
        return ResponseEntity.ok(response);
    }
}
