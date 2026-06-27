package com.campusplacement.service;

import com.campusplacement.dto.AuthRequest;
import com.campusplacement.dto.RegisterRequest;
import com.campusplacement.dto.AuthResponse;
import com.campusplacement.enums.Role;
import com.campusplacement.entity.User;
import com.campusplacement.repository.UserRepository;
import com.campusplacement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.campusplacement.dto.request.ForgotPasswordRequest;
import com.campusplacement.dto.request.ResetPasswordRequest;
import com.campusplacement.entity.PasswordResetToken;
import com.campusplacement.repository.PasswordResetTokenRepository;
import com.campusplacement.service.EmailService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Transactional
    public void forgotPassword(
            ForgotPasswordRequest request
    ) {

        User user = userRepository.findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        passwordResetTokenRepository
                .findByUser(user)
                .ifPresent(existing ->
                        passwordResetTokenRepository
                                .delete(existing));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken =
                PasswordResetToken.builder()
                        .token(token)
                        .user(user)
                        .expiryDate(
                                LocalDateTime.now()
                                        .plusMinutes(15)
                        )
                        .build();

        passwordResetTokenRepository
                .save(resetToken);

        String resetLink =
                "http://localhost:5173/reset-password?token="
                        + token;

        emailService.sendPasswordResetEmail(
                user.getEmail(),
                resetLink
        );
    }
    @Transactional
    public void resetPassword(
            ResetPasswordRequest request
    ) {

        PasswordResetToken resetToken =
                passwordResetTokenRepository
                        .findByToken(
                                request.getToken()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid reset token"));

        if (resetToken.getExpiryDate()
                .isBefore(
                        LocalDateTime.now())) {

            passwordResetTokenRepository
                    .delete(resetToken);

            throw new RuntimeException(
                    "Reset token expired");
        }

        User user = resetToken.getUser();

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        passwordResetTokenRepository
                .delete(resetToken);
    }







    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (request.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException(
                    "Admin accounts cannot be created through registration.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "An account with this email already exists.");
        }

        boolean isActive = request.getRole() == Role.STUDENT;

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isActive(isActive)
                .build();

        userRepository.save(user);

        String message = request.getRole() == Role.RECRUITER
                ? "Registration successful. Await admin approval."
                : "Registration successful.";

        return AuthResponse.builder()
                .token(null)
                .role(request.getRole().name())
                .message(message)
                .build();
    }

    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException(
                        "Authenticated user not found."
                ));

        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .role(user.getRole().name())
                .message("Login successful.")
                .build();
    }
}