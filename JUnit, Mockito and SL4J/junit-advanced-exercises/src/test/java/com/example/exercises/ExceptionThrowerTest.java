package com.example.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Exercise 4: Exception Testing
 * Tests that ExceptionThrower.throwException() throws the expected exception.
 */
class ExceptionThrowerTest {

    private final ExceptionThrower exceptionThrower = new ExceptionThrower();

    @Test
    void testThrowException_withNegativeValue_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> exceptionThrower.throwException(-1)
        );
        assertEquals("Value cannot be negative: -1", exception.getMessage());
    }

    @Test
    void testThrowException_withPositiveValue_doesNotThrow() {
        assertDoesNotThrow(() -> exceptionThrower.throwException(5));
    }

    @Test
    void testThrowException_withZero_returnsZero() {
        int result = assertDoesNotThrow(() -> exceptionThrower.throwException(0));
        assertEquals(0, result);
    }
}
