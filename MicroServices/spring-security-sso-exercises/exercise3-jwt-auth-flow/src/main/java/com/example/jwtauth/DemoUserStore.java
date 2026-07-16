package com.example.jwtauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Hardcoded demo users so the exercise runs with zero setup.
 * A real app would replace this with a database-backed UserDetailsService.
 * Passwords are still BCrypt-hashed here to show the right pattern even
 * in a demo (never store or compare plaintext passwords).
 */
@Component
public class DemoUserStore {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, String> usersByUsername;

    public boolean authenticate(String username, String rawPassword) {
        String encodedPassword = getUsers().get(username);
        return encodedPassword != null && passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private Map<String, String> getUsers() {
        if (usersByUsername == null) {
            usersByUsername = Map.of(
                    "sravani", passwordEncoder.encode("password123"),
                    "admin", passwordEncoder.encode("admin123")
            );
        }
        return usersByUsername;
    }
}
