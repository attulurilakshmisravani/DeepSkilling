package com.example.exercises;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercise 1: Parameterized Tests
 * Tests EvenChecker.isEven() with multiple inputs using @ParameterizedTest.
 */
class EvenCheckerTest {

    private final EvenChecker evenChecker = new EvenChecker();

    @ParameterizedTest(name = "{0} should be even")
    @ValueSource(ints = {0, 2, 4, 100, -8})
    @DisplayName("Even numbers should return true")
    void testIsEven_withEvenNumbers(int number) {
        assertTrue(evenChecker.isEven(number));
    }

    @ParameterizedTest(name = "{0} should be odd")
    @ValueSource(ints = {1, 3, 7, 99, -5})
    @DisplayName("Odd numbers should return false")
    void testIsEven_withOddNumbers(int number) {
        assertFalse(evenChecker.isEven(number));
    }

    // Bonus: demonstrate @CsvSource mapping input -> expected result
    @ParameterizedTest(name = "isEven({0}) = {1}")
    @CsvSource({
            "0, true",
            "1, false",
            "2, true",
            "-4, true",
            "-7, false"
    })
    void testIsEven_withCsvSource(int number, boolean expected) {
        assertTrue(evenChecker.isEven(number) == expected);
    }
}
