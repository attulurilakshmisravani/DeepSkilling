package com.example.jwtauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Autowired
    private SecretKey jwtSigningKey;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * Creates a signed JWT for the given username using the current
     * jjwt 0.12.x fluent API (Jwts.builder().subject(...).signWith(key)),
     * which replaces the deprecated 0.9.1 style used in the original
     * exercise (Jwts.builder().setSubject(...).signWith(SignatureAlgorithm.HS256, secret)).
     */
    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtConfig.getExpirationMs());

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(validity)
                .signWith(jwtSigningKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(jwtSigningKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Covers expired, malformed, or signature-mismatched tokens
            return false;
        }
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(jwtSigningKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        // Demo only: every authenticated user gets a single ROLE_USER
        // authority. A real app would look up roles from a user store.
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
