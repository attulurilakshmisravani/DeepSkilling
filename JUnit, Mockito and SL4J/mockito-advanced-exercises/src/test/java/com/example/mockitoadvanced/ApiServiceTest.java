package com.example.mockitoadvanced;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Exercise 2: Mocking External Services (RESTful APIs)
 * Mocks the RestClient and stubs getResponse() to return a predefined
 * response, then verifies ApiService.fetchData() wraps it as expected.
 */
class ApiServiceTest {

    @Test
    void testServiceWithMockRestClient() {
        // 1. Create a mock REST client using Mockito.
        RestClient mockRestClient = mock(RestClient.class);

        // 2. Stub the REST client methods to return predefined responses.
        when(mockRestClient.getResponse()).thenReturn("Mock Response");

        // 3. Write a test to verify the service logic using the mocked REST client.
        ApiService apiService = new ApiService(mockRestClient);
        String result = apiService.fetchData();

        assertEquals("Fetched Mock Response", result);
    }
}
