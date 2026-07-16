package com.example.jwtauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private DemoUserStore demoUserStore;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public record LoginRequest(String username, String password) {}

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        if (!demoUserStore.authenticate(request.username(), request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        String token = jwtTokenProvider.createToken(request.username());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
