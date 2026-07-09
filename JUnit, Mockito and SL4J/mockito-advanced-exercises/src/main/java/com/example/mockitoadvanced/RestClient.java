package com.example.mockitoadvanced;

/**
 * Exercise 2: Mocking External Services (RESTful APIs)
 * Represents a client that talks to a remote RESTful API.
 * We mock this interface in tests instead of making real HTTP calls.
 */
public interface RestClient {

    String getResponse();
}
