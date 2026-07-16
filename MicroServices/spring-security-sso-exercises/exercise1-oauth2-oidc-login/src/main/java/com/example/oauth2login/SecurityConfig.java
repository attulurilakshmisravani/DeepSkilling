package com.example.oauth2login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * NOTE: WebSecurityConfigurerAdapter (used in older tutorials) was removed
 * in Spring Security 6 / Spring Boot 3. The current approach is to expose a
 * SecurityFilterChain bean instead, as shown below.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/user", true));

        return http.build();
    }
}
