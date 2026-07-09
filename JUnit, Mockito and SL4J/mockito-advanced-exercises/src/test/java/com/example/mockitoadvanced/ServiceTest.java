package com.example.mockitoadvanced;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Exercise 1: Mocking Databases and Repositories
 * Mocks the Repository and stubs getData() to return predefined data,
 * then verifies Service.processData() applies the expected processing.
 */
class ServiceTest {

    @Test
    void testServiceWithMockRepository() {
        // 1. Create a mock repository using Mockito.
        Repository mockRepository = mock(Repository.class);

        // 2. Stub the repository methods to return predefined data.
        when(mockRepository.getData()).thenReturn("Mock Data");

        // 3. Write a test to verify the service logic using the mocked repository.
        Service service = new Service(mockRepository);
        String result = service.processData();

        assertEquals("Processed Mock Data", result);
    }
}
