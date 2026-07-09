package com.example.mockito;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Exercise 7: Handling Void Methods with Exceptions
 * Stubs a void method (saveData) to throw an exception when called,
 * and verifies both that the exception propagates and that the mock
 * was actually invoked.
 */
class VoidMethodsWithExceptionsTest {

    @Test
    void testSaveData_throwsExceptionWhenApiFails() {
        // 1. Create a mock object.
        ExternalApi mockApi = mock(ExternalApi.class);

        // 2. Stub the void method to throw an exception.
        doThrow(new RuntimeException("External API is down"))
                .when(mockApi)
                .saveData("bad data");

        MyService service = new MyService(mockApi);

        // The exception thrown by the mock should propagate up through
        // MyService.saveData() since MyService doesn't catch it.
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveData("bad data")
        );
        assertEquals("External API is down", exception.getMessage());

        // 3. Verify the interaction still happened despite the exception.
        verify(mockApi).saveData("bad data");
    }
}
