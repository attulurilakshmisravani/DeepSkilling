package com.example.mockito;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Exercise 5: Mocking and Stubbing with Multiple Returns
 * Stubs getData() to return a different value on each consecutive call,
 * simulating an API whose response changes over time (e.g. polling).
 */
class MultipleReturnsTest {

    @Test
    void testFetchData_returnsDifferentValuesOnConsecutiveCalls() {
        // 1. Create a mock object for the external API.
        ExternalApi mockApi = mock(ExternalApi.class);

        // 2. Stub the method to return different values on consecutive calls.
        when(mockApi.getData())
                .thenReturn("First Call")
                .thenReturn("Second Call")
                .thenReturn("Third Call");

        // 3. Write a test case that uses the mock object.
        MyService service = new MyService(mockApi);

        assertEquals("First Call", service.fetchData());
        assertEquals("Second Call", service.fetchData());
        assertEquals("Third Call", service.fetchData());

        // Once the stubbed sequence is exhausted, Mockito keeps returning
        // the last stubbed value on further calls.
        assertEquals("Third Call", service.fetchData());
    }

    @Test
    void testFetchDataThreeTimes_usingVarargsShorthand() {
        ExternalApi mockApi = mock(ExternalApi.class);

        // Equivalent shorthand: first argument is the first call's return
        // value, the rest are for each subsequent call.
        when(mockApi.getData()).thenReturn("A", "B", "C");

        MyService service = new MyService(mockApi);
        String result = service.fetchDataThreeTimes();

        assertEquals("A,B,C", result);
    }
}
