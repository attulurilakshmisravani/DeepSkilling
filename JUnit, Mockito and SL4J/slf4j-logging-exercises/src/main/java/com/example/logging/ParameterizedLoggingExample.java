package com.example.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exercise 2: Parameterized Logging
 *
 * Instead of building log messages with string concatenation (which always
 * runs, even if the log level is disabled), SLF4J lets you use {} placeholders.
 * The message is only formatted if the log level is actually enabled, which
 * is both faster and cleaner to read.
 */
public class ParameterizedLoggingExample {

    private static final Logger logger = LoggerFactory.getLogger(ParameterizedLoggingExample.class);

    public static void main(String[] args) {
        String username = "Sravani";
        int loginAttempts = 3;

        // Single placeholder
        logger.info("User {} logged in successfully", username);

        // Multiple placeholders
        logger.warn("User {} failed to log in after {} attempts", username, loginAttempts);

        // Bad practice for comparison (avoid this): string concatenation always
        // builds the string even if INFO/DEBUG logging is disabled.
        // logger.debug("User " + username + " has " + loginAttempts + " attempts");

        // Good practice: parameterized form only formats the string when needed
        logger.debug("User {} has {} attempts remaining", username, 5 - loginAttempts);

        // Three or more placeholders - use an Object[] array
        double accountBalance = 1500.75;
        logger.info("Account summary - user: {}, attempts: {}, balance: {}",
                username, loginAttempts, accountBalance);

        // Parameterized logging combined with an exception (exception must be last)
        try {
            validateAttempts(loginAttempts);
        } catch (IllegalStateException e) {
            logger.error("Validation failed for user {} after {} attempts", username, loginAttempts, e);
        }
    }

    private static void validateAttempts(int attempts) {
        if (attempts >= 3) {
            throw new IllegalStateException("Too many login attempts");
        }
    }

}
