package com.example.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Invoked by the gateway route's fallback URI when the circuit is open
 * or the downstream call times out.
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback/flaky")
    public Mono<Map<String, Object>> flakyFallback() {
        return Mono.just(Map.of(
                "message", "The downstream service is currently unavailable. Please try again shortly.",
                "fallback", true
        ));
    }
}
