package com.example.jwtauth;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SecureController {

    @GetMapping("/secure")
    public Map<String, Object> secure(Authentication authentication) {
        return Map.of(
                "message", "This is a secure endpoint",
                "authenticatedAs", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }
}
