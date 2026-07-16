package com.example.backend;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Simulates an unreliable downstream service so the gateway's circuit
 * breaker has something to trip on.
 *
 * - GET /flaky always fails while "failing" mode is on.
 * - POST /toggle-failure flips failing mode on/off, so you can control
 *   exactly when the circuit breaker should open/close during testing.
 * - GET /slow always takes 3 seconds, to demonstrate the TimeLimiter.
 */
@RestController
public class UnreliableController {

    private final AtomicBoolean failing = new AtomicBoolean(false);

    @GetMapping("/flaky")
    public Map<String, Object> flaky() {
        if (failing.get()) {
            throw new RuntimeException("Simulated downstream failure");
        }
        return Map.of("message", "Success from backend-service", "failing", failing.get());
    }

    @PostMapping("/toggle-failure")
    public Map<String, Object> toggleFailure() {
        boolean now = failing.updateAndGet(v -> !v);
        return Map.of("failing", now);
    }

    @GetMapping("/slow")
    public Map<String, Object> slow() throws InterruptedException {
        Thread.sleep(3000);
        return Map.of("message", "Slow response completed after 3 seconds");
    }
}
