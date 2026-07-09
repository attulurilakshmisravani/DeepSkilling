package com.example.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exercise 3: Using Different Appenders
 *
 * The appenders themselves are configured entirely in
 * src/main/resources/logback.xml (a "console" appender and a "file"
 * appender, both attached to the root logger). This class doesn't need to
 * know anything about appenders directly - it just logs normally, and
 * logback.xml decides where each message actually ends up.
 *
 * Run this class and you'll see every message printed to the console AND
 * appended to app.log in the project's working directory.
 */
public class AppenderExample {

    private static final Logger logger = LoggerFactory.getLogger(AppenderExample.class);

    public static void main(String[] args) {
        logger.debug("Application starting up (debug level - goes to both appenders)");
        logger.info("AppenderExample is running");
        logger.warn("This warning is written to BOTH console and app.log");
        logger.error("This error is written to BOTH console and app.log");

        performTask();

        logger.info("AppenderExample finished. Check app.log in the project root!");
    }

    private static void performTask() {
        logger.debug("Starting performTask()");
        for (int i = 1; i <= 3; i++) {
            logger.info("Processing item {} of 3", i);
        }
        logger.debug("Finished performTask()");
    }

}
