package com.example.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * Returns which instance (port) handled the request, so that when this
 * service is run multiple times behind the gateway's load balancer, you
 * can see requests being distributed across instances.
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        return Map.of(
                "message", "Hello from backend instance",
                "port", port,
                "timestamp", Instant.now().toString()
        );
    }
}
