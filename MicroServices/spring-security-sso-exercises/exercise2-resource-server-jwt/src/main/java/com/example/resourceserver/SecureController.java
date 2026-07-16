package com.example.resourceserver;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SecureController {

    @GetMapping("/public")
    public Map<String, String> publicEndpoint() {
        return Map.of("message", "This endpoint is open to everyone");
    }

    @GetMapping("/secure")
    public Map<String, Object> secure(Jwt jwt) {
        return Map.of(
                "message", "This is a secure endpoint",
                "subject", jwt.getSubject(),
                "issuedAt", jwt.getIssuedAt().toString(),
                "expiresAt", jwt.getExpiresAt().toString(),
                "claims", jwt.getClaims()
        );
    }
}
