package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Exercise 3: Assertions in JUnit
 *
 * Demonstrates the different assertion methods available in JUnit.
 */
public class Exercise3_AssertionsTest {

    @Test
    public void testAssertions() {
        // Assert equals
        assertEquals(5, 2 + 3);

        // Assert true
        assertTrue(5 > 3);

        // Assert false
        assertFalse(5 < 3);

        // Assert null
        assertNull(null);

        // Assert not null
        assertNotNull(new Object());

        // Assert same (reference equality)
        String s1 = "hello";
        String s2 = s1;
        assertSame(s1, s2);

        // Assert not same
        String s3 = new String("hello");
        assertNotSame(s1, s3);

        // Assert arrays equal
        int[] expectedArray = {1, 2, 3};
        int[] actualArray = {1, 2, 3};
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void testAssertionsWithCalculator() {
        Calculator calculator = new Calculator();

        assertEquals(10, calculator.add(6, 4));
        assertTrue(calculator.isEven(4));
        assertFalse(calculator.isEven(5));

        // Assert an exception is thrown
        try {
            calculator.divide(10, 0);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot divide by zero", e.getMessage());
        }
    }
}
