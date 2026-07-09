package com.example.exercises;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Exercise 2: Test Suites and Categories
 * Groups related test classes into a single suite using
 * @Suite and @SelectClasses annotations.
 *
 * Run this class to execute all the listed test classes together.
 */
@Suite
@SelectClasses({
        EvenCheckerTest.class,
        OrderedTests.class,
        ExceptionThrowerTest.class,
        PerformanceTesterTest.class
})
class AllTests {
    // No body needed; annotations configure the suite.
}
