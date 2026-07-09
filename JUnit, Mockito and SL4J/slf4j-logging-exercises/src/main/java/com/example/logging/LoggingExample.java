package com.example.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exercise 1: Logging Error Messages and Warning Levels
 *
 * Demonstrates the two most commonly used "something went wrong" log
 * levels in SLF4J:
 *   - error(): something failed and needs attention
 *   - warn():  something unexpected happened but the app can continue
 */
public class LoggingExample {

    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {
        logger.error("This is an error message");
        logger.warn("This is a warning message");

        // A couple of more realistic examples of each level:
        logger.warn("Disk space is running low: only 500MB remaining");

        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // Passing the exception as the last argument logs the full stack trace
            logger.error("Failed to divide numbers", e);
        }
    }

}
