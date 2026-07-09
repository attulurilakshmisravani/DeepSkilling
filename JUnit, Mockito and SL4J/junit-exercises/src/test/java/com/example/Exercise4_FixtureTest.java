package com.example;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Exercise 4: Arrange-Act-Assert (AAA) Pattern, Test Fixtures,
 * Setup and Teardown Methods in JUnit
 *
 * Demonstrates:
 * - The AAA pattern within each test
 * - @Before / @After for per-test setup and teardown
 * - @BeforeClass / @AfterClass for one-time setup and teardown
 */
public class Exercise4_FixtureTest {

    private Calculator calculator;

    @BeforeClass
    public static void setUpClass() {
        // Runs once before any test in this class.
        System.out.println("Starting Exercise4_FixtureTest test suite...");
    }

    @AfterClass
    public static void tearDownClass() {
        // Runs once after all tests in this class have finished.
        System.out.println("Finished Exercise4_FixtureTest test suite.");
    }

    @Before
    public void setUp() {
        // Runs before each test method: creates a fresh fixture.
        calculator = new Calculator();
    }

    @After
    public void tearDown() {
        // Runs after each test method: cleans up the fixture.
        calculator = null;
    }

    @Test
    public void testAdd_usingAAA() {
        // Arrange
        int a = 4;
        int b = 6;

        // Act
        int result = calculator.add(a, b);

        // Assert
        assertEquals(10, result);
    }

    @Test
    public void testSubtract_usingAAA() {
        // Arrange
        int a = 9;
        int b = 5;

        // Act
        int result = calculator.subtract(a, b);

        // Assert
        assertEquals(4, result);
    }

    @Test
    public void testIsEven_usingAAA() {
        // Arrange
        int number = 8;

        // Act
        boolean result = calculator.isEven(number);

        // Assert
        assertTrue(result);
    }
}
