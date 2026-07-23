package com.cognizant.springlearn.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * REST - Create authentication service that returns JWT
 *
 * URL: /authenticate  (GET)
 * Credentials are passed using HTTP Basic auth (curl -u user:pwd ...).
 * This controller reads the Authorization header, decodes the user id,
 * generates a JWT for that user, and returns it in the response body.
 *
 * Sample Request:  curl -s -u user:pwd http://localhost:8090/authenticate
 * Sample Response: {"token":"eyJhbGciOiJIUzI1NiJ9. ..."}
 */
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    // Same signing key used later by JwtAuthorizationFilter to validate the token.
    private static final String SECRET_KEY = "secretkey";

    // Token validity: 20 minutes (in milliseconds)
    private static final long TOKEN_VALIDITY = 1200000;

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.info("START - authenticate()");
        LOGGER.debug("authHeader: {}", authHeader);

        Map<String, String> map = new HashMap<>();

        String user = getUser(authHeader);
        String token = generateJwt(user);
        map.put("token", token);

        LOGGER.info("END - authenticate()");
        return map;
    }

    /**
     * Reads the Base64 encoded "user:password" text following "Basic "
     * in the Authorization header, decodes it, and returns the user id
     * (the text before the colon).
     */
    private String getUser(String authHeader) {
        LOGGER.info("START - getUser()");

        String encodedCredentials = authHeader.replace("Basic ", "");
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String decodedCredentials = new String(decodedBytes);
        String user = decodedCredentials.split(":")[0];

        LOGGER.debug("user: {}", user);
        LOGGER.info("END - getUser()");
        return user;
    }

    /**
     * Generates a signed JWT for the given user, valid for 20 minutes.
     */
    private String generateJwt(String user) {
        LOGGER.info("START - generateJwt()");

        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user);
        // Set the token issue time as current time
        builder.setIssuedAt(new Date());
        // Set the token expiry as 20 minutes from now
        builder.setExpiration(new Date((new Date()).getTime() + TOKEN_VALIDITY));
        builder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
        String token = builder.compact();

        LOGGER.debug("token: {}", token);
        LOGGER.info("END - generateJwt()");
        return token;
    }
}
