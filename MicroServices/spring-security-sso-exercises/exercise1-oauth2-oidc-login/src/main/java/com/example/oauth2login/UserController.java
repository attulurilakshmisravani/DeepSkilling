package com.example.oauth2login;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class UserController {

    /**
     * Returns the authenticated principal's name (works for any OAuth2 login).
     */
    @GetMapping("/user")
    public Map<String, Object> user(Principal principal) {
        if (principal instanceof OidcUser oidcUser) {
            // OIDC gives us richer claims than a plain OAuth2 principal
            return Map.of(
                    "name", oidcUser.getFullName() != null ? oidcUser.getFullName() : oidcUser.getName(),
                    "email", oidcUser.getEmail() != null ? oidcUser.getEmail() : "n/a",
                    "claims", oidcUser.getClaims()
            );
        }
        return Map.of("name", principal.getName());
    }
}
