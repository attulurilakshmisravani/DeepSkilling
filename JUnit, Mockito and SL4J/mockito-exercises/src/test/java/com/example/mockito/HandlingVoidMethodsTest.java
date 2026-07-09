package com.example.mockito;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Exercise 4: Handling Void Methods
 * Stubs a void method (saveData) with doNothing() and verifies it was
 * called with the expected argument.
 */
class HandlingVoidMethodsTest {

    @Test
    void testSaveData_voidMethodStubbedAndVerified() {
        // 1. Create a mock object.
        ExternalApi mockApi = mock(ExternalApi.class);

        // 2. Stub the void method. doNothing() is actually the default
        // behavior for void methods on a mock, but it's shown explicitly
        // here for clarity / to demonstrate the API.
        doNothing().when(mockApi).saveData("some data");

        MyService service = new MyService(mockApi);
        service.saveData("some data");

        // 3. Verify the interaction.
        verify(mockApi).saveData("some data");
    }
}
