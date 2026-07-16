package com.example.oauth2login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public landing page. Spring Security's default /login page also works,
 * but this gives a friendlier entry point.
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
                <html>
                  <body style="font-family: sans-serif; padding: 2rem;">
                    <h2>OAuth2 / OIDC Login Demo</h2>
                    <p>Click below to sign in with Google and see your authenticated
                       user info.</p>
                    <a href="/oauth2/authorization/google">
                      <button style="padding: 0.5rem 1rem;">Login with Google</button>
                    </a>
                  </body>
                </html>
                """;
    }
}
