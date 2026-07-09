package com.example.mockitoadvanced;

/**
 * Exercise 1: Mocking Databases and Repositories
 * Represents a data-access layer (e.g. backed by a database).
 * We mock this interface in tests instead of hitting a real database.
 */
public interface Repository {

    String getData();
}
