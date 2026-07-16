package com.example.resourceserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * In a real deployment you'd usually point Spring at an external
 * Authorization Server via `spring.security.oauth2.resourceserver.jwt.issuer-uri`
 * (as in the original exercise) and Spring Security would auto-discover
 * its JWK Set endpoint to fetch verification keys.
 *
 * For a self-contained, offline-runnable exercise, this bean instead
 * builds a JwtDecoder directly from a shared HMAC secret, so tokens
 * signed by {@link TokenGenerator} (using the same secret) can be
 * validated here without standing up a separate Authorization Server.
 */
@Configuration
public class JwtDecoderConfig {

    @Value("${app.jwt.secret}")
    private String secret;

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
