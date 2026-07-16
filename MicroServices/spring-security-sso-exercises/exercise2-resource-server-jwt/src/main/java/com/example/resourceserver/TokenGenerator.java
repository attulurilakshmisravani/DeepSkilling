package com.example.resourceserver;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

/**
 * Standalone utility (not a Spring bean) that mints a JWT signed with the
 * same shared secret the resource server uses to validate tokens
 * ({@link JwtDecoderConfig}). This stands in for a real Authorization
 * Server so you can test /secure without one.
 *
 * Run it directly from your IDE (right-click > Run Java) or with:
 *   mvn compile exec:java -Dexec.mainClass=com.example.resourceserver.TokenGenerator
 *
 * IMPORTANT: the secret below must match `app.jwt.secret` in
 * application.yml exactly.
 */
public class TokenGenerator {

    private static final String SECRET = "this-is-a-demo-secret-key-change-me-1234567890";

    public static void main(String[] args) throws Exception {
        String username = args.length > 0 ? args[0] : "sravani";

        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3_600_000); // 1 hour

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(now)
                .expirationTime(expiry)
                .claim("roles", "USER")
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        signedJWT.sign(new MACSigner(SECRET.getBytes()));

        String token = signedJWT.serialize();

        System.out.println("Generated JWT for subject '" + username + "':\n");
        System.out.println(token);
        System.out.println("\nTest it with:");
        System.out.println("curl -H \"Authorization: Bearer " + token + "\" http://localhost:8080/secure");
    }
}
