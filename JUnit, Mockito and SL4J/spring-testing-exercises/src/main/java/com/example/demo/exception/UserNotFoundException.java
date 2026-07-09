package com.example.demo.exception;

import java.util.NoSuchElementException;

/**
 * Thrown when a requested User cannot be found.
 * Extends NoSuchElementException so it plugs straight into the
 * GlobalExceptionHandler used in Exercise 8.
 */
public class UserNotFoundException extends NoSuchElementException {

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }

}
