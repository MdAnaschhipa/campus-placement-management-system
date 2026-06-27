package com.campusplacement.config;

import com.campusplacement.enums.Role;
import com.campusplacement.entity.User;
import com.campusplacement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@campusplacement.com";
    private static final String ADMIN_PASSWORD = "Admin@123";
    private static final String ADMIN_FULL_NAME = "System Administrator";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.existsByEmail(ADMIN_EMAIL)) {
            return;
        }

        User admin = User.builder()
                .fullName(ADMIN_FULL_NAME)
                .email(ADMIN_EMAIL)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(Role.ADMIN)
                .isActive(true)
                .build();

        userRepository.save(admin);
    }
}