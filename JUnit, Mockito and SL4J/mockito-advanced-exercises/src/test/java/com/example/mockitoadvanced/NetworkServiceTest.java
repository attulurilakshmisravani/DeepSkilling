package com.example.mockitoadvanced;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Exercise 4: Mocking Network Interactions
 * Mocks the NetworkClient and stubs connect() to simulate a successful
 * connection, then verifies NetworkService.connectToServer() behaves
 * as expected.
 */
class NetworkServiceTest {

    @Test
    void testServiceWithMockNetworkClient() {
        // 1. Create a mock network client using Mockito.
        NetworkClient mockNetworkClient = mock(NetworkClient.class);

        // 2. Stub the network client methods to simulate network interactions.
        when(mockNetworkClient.connect()).thenReturn("Mock Connection");

        // 3. Write a test to verify the service logic using the mocked network client.
        NetworkService networkService = new NetworkService(mockNetworkClient);
        String result = networkService.connectToServer();

        assertEquals("Connected to Mock Connection", result);
    }
}
