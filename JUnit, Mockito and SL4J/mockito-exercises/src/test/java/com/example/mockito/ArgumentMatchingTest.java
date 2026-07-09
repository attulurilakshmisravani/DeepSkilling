package com.example.mockito;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Exercise 3: Argument Matching
 * Uses Mockito argument matchers (anyString(), eq(), startsWith()) both to
 * stub behavior for any input and to verify the call happened with a
 * specific argument.
 */
class ArgumentMatchingTest {

    @Test
    void testFetchDataById_withAnyStringMatcher() {
        // 1. Create a mock object.
        ExternalApi mockApi = mock(ExternalApi.class);

        // Stub: whatever id is passed in, return "Generic Data".
        when(mockApi.getDataById(anyString())).thenReturn("Generic Data");

        MyService service = new MyService(mockApi);

        // 2. Call the method with specific arguments.
        String result = service.fetchDataById("user-42");

        assertEquals("Generic Data", result);

        // 3. Use argument matchers to verify the interaction.
        verify(mockApi).getDataById(anyString());
    }

    @Test
    void testFetchDataById_withExactArgumentMatcher() {
        ExternalApi mockApi = mock(ExternalApi.class);
        when(mockApi.getDataById(eq("user-42"))).thenReturn("Specific Data");

        MyService service = new MyService(mockApi);
        String result = service.fetchDataById("user-42");

        assertEquals("Specific Data", result);

        // Verify it was called with exactly this argument, not just "any string".
        verify(mockApi).getDataById(eq("user-42"));
        verify(mockApi).getDataById(startsWith("user-"));
    }
}
