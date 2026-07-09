package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Exercise 1: Basic Unit Test for a Service Method
 * Plain JUnit 5 test - no Spring context needed since CalculatorService
 * has no dependencies to mock.
 */
class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    @DisplayName("add() should return the sum of two positive numbers")
    void testAddPositiveNumbers() {
        int result = calculatorService.add(2, 3);
        assertEquals(5, result);
    }

    @Test
    @DisplayName("add() should handle negative numbers correctly")
    void testAddNegativeNumbers() {
        int result = calculatorService.add(-4, -6);
        assertEquals(-10, result);
    }

    @Test
    @DisplayName("add() should handle zero")
    void testAddWithZero() {
        int result = calculatorService.add(10, 0);
        assertEquals(10, result);
    }

}
