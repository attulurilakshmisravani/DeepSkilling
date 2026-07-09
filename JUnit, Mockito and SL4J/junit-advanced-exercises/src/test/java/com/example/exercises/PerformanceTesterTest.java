package com.example.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

/**
 * Exercise 5: Timeout and Performance Testing
 * Tests that PerformanceTester.performTask() completes within a time limit,
 * demonstrating three different ways JUnit 5 supports timeout testing.
 */
class PerformanceTesterTest {

    private final PerformanceTester performanceTester = new PerformanceTester();

    // Approach 1: Declarative timeout using @Timeout annotation.
    // Fails the test automatically if it runs longer than 500 ms.
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testPerformTask_withTimeoutAnnotation() throws InterruptedException {
        performanceTester.performTask(100);
    }

    // Approach 2: Programmatic timeout using assertTimeout().
    // Lets the task run to completion, then checks elapsed time.
    @Test
    void testPerformTask_withAssertTimeout() {
        assertTimeout(java.time.Duration.ofMillis(500), () -> {
            performanceTester.performTask(100);
        });
    }

    // Approach 3: Preemptive timeout using assertTimeoutPreemptively().
    // Aborts the task in a separate thread as soon as the timeout is hit.
    @Test
    void testPerformTask_withAssertTimeoutPreemptively() {
        assertTimeoutPreemptively(java.time.Duration.ofMillis(500), () -> {
            performanceTester.performTask(100);
        });
    }
}
