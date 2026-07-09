package com.example.mockito;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Exercise 2: Verifying Interactions
 * Ensures that MyService.fetchData() actually calls getData() on the mock,
 * and that it is called exactly once.
 */
class VerifyingInteractionsTest {

    @Test
    void testVerifyInteraction() {
        // 1. Create a mock object.
        ExternalApi mockApi = mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        // 2. Call the method with specific arguments (no args here).
        service.fetchData();

        // 3. Verify the interaction happened exactly once.
        verify(mockApi).getData();
        verify(mockApi, times(1)).getData();
    }

    @Test
    void testMethodNeverCalledWhenNotInvoked() {
        ExternalApi mockApi = mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        // We never call service.saveData(...), so saveData should never
        // have been invoked on the mock.
        verify(mockApi, never()).saveData("anything");

        // Sanity: instantiate service so it's not flagged unused.
        assert service != null;
    }
}
