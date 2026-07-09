package com.example.mockito;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Exercise 1: Mocking and Stubbing
 * Mocks the ExternalApi and stubs getData() to return a predefined value,
 * then verifies MyService.fetchData() returns that stubbed value.
 */
class MockingAndStubbingTest {

    @Test
    void testExternalApi() {
        // 1. Create a mock object for the external API.
        ExternalApi mockApi = mock(ExternalApi.class);

        // 2. Stub the method to return a predefined value.
        when(mockApi.getData()).thenReturn("Mock Data");

        // 3. Write a test case that uses the mock object.
        MyService service = new MyService(mockApi);
        String result = service.fetchData();

        assertEquals("Mock Data", result);
    }
}
