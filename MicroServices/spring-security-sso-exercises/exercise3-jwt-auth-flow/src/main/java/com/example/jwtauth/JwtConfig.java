package com.example.jwtauth;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtConfig {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    /**
     * jjwt 0.12.x requires a proper java.security.Key, not a raw string
     * (unlike the old 0.9.1 API used in the original exercise, which
     * accepted `signWith(SignatureAlgorithm.HS256, secretString)`
     * directly).
     */
    @Bean
    public SecretKey jwtSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public long getExpirationMs() {
        return expirationMs;
    }
}
