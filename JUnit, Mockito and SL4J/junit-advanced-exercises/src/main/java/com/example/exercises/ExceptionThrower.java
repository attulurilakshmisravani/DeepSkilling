package com.example.exercises;

/**
 * Exercise 4: Exception Testing
 * A class with a method that throws an exception under certain conditions.
 */
public class ExceptionThrower {

    /**
     * Throws an IllegalArgumentException if the input value is negative.
     *
     * @param value the value to check
     * @return the value itself if non-negative
     */
    public int throwException(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative: " + value);
        }
        return value;
    }
}
