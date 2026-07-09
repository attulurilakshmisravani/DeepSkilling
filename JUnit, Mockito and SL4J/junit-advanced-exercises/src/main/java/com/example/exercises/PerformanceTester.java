package com.example.exercises;

/**
 * Exercise 5: Timeout and Performance Testing
 * A class with a method that simulates a task and should complete
 * within a specified time limit.
 */
public class PerformanceTester {

    /**
     * Simulates a task that takes some time to complete.
     *
     * @param delayMillis how long the task should take, in milliseconds
     */
    public void performTask(long delayMillis) throws InterruptedException {
        Thread.sleep(delayMillis);
    }
}
