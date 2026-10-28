package com.allur.testdrive.config;

import com.allur.testdrive.model.Role;
import com.allur.testdrive.model.User;
import com.allur.testdrive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@allur.kz")) {
            User admin = User.builder()
                    .email("admin@allur.kz")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("Admin")
                    .lastName("Allur")
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            System.out.println("==============================================");
            System.out.println("Admin created: admin@allur.kz / admin123");
            System.out.println("==============================================");
        }
    }
}
