package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercise 9: Parameterized Test with JUnit 5
 *
 * @ParameterizedTest runs the same test logic against multiple inputs,
 * avoiding copy-pasted @Test methods that only differ by data.
 */
class CalculatorServiceParameterizedTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    // "a, b, expectedSum"
    @ParameterizedTest(name = "add({0}, {1}) = {2}")
    @CsvSource({
            "1, 1, 2",
            "2, 3, 5",
            "-1, 1, 0",
            "0, 0, 0",
            "100, 200, 300",
            "-5, -5, -10"
    })
    @DisplayName("add() should return the correct sum for various input pairs")
    void testAddVariousInputs(int a, int b, int expectedSum) {
        assertEquals(expectedSum, calculatorService.add(a, b));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 10, -5, 1000})
    @DisplayName("add(n, 0) should always equal n")
    void testAddZeroIsIdentity(int n) {
        assertEquals(n, calculatorService.add(n, 0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Sravani", "Ramesh", "A", "Very Long Name Example"})
    @DisplayName("Names used for parameterized string tests should never be blank")
    void testNamesAreNotBlank(String name) {
        assertTrue(name != null && !name.isBlank());
    }

}
