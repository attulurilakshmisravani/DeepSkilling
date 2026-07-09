package com.example.mockitoadvanced;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Exercise 5: Mocking Multiple Return Values
 * Stubs Repository.getData() to return different values on consecutive
 * calls, then verifies Service.processData() reflects each call correctly.
 */
class MultiReturnServiceTest {

    @Test
    void testServiceWithMultipleReturnValues() {
        // 1. Create a mock object using Mockito.
        Repository mockRepository = mock(Repository.class);

        // 2. Stub the method to return different values on consecutive calls.
        when(mockRepository.getData())
                .thenReturn("First Mock Data")
                .thenReturn("Second Mock Data");

        // 3. Write a test to verify the service logic using the mocked object.
        Service service = new Service(mockRepository);
        String firstResult = service.processData();
        String secondResult = service.processData();

        assertEquals("Processed First Mock Data", firstResult);
        assertEquals("Processed Second Mock Data", secondResult);
    }
}
