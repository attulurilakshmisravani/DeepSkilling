package com.example.mockito;

/**
 * Represents a third-party / external API that MyService depends on.
 * In the real world this might wrap an HTTP client, a database driver, etc.
 * We mock this interface in the tests instead of hitting a real API.
 */
public interface ExternalApi {

    /**
     * Fetches some data with no arguments.
     */
    String getData();

    /**
     * Fetches data for a specific id.
     */
    String getDataById(String id);

    /**
     * Saves data. Void method used for Exercises 4 and 7.
     */
    void saveData(String data);

    /**
     * Performs some side-effecting action with no return value.
     * Used together with logMessage() to demonstrate order verification.
     */
    void performAction();

    /**
     * Logs a message. Used together with performAction() for Exercise 6.
     */
    void logMessage(String message);
}
