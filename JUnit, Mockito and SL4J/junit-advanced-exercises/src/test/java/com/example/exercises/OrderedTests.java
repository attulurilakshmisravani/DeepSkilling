package com.example.exercises;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Exercise 3: Test Execution Order
 * Demonstrates controlling test execution order using
 * @TestMethodOrder and @Order annotations.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTests {

    private static final StringBuilder EXECUTION_LOG = new StringBuilder();

    @Test
    @Order(1)
    void firstTest() {
        System.out.println("Executing firstTest (Order 1)");
        EXECUTION_LOG.append("1");
    }

    @Test
    @Order(2)
    void secondTest() {
        System.out.println("Executing secondTest (Order 2)");
        EXECUTION_LOG.append("2");
    }

    @Test
    @Order(3)
    void thirdTest() {
        System.out.println("Executing thirdTest (Order 3)");
        EXECUTION_LOG.append("3");
        // By now the log should read "123" confirming the execution order.
        assert EXECUTION_LOG.toString().equals("123");
    }
}
