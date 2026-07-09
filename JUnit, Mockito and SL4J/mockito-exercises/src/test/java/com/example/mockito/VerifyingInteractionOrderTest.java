package com.example.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

/**
 * Exercise 6: Verifying Interaction Order
 * Ensures that performAction() is called before logMessage() when
 * MyService.processData() runs.
 */
class VerifyingInteractionOrderTest {

    @Test
    void testProcessData_callsMethodsInExpectedOrder() {
        // 1. Create a mock object.
        ExternalApi mockApi = mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        // 2. Call the methods in a specific order (via processData()).
        service.processData();

        // 3. Verify the interaction order.
        InOrder inOrder = inOrder(mockApi);
        inOrder.verify(mockApi).performAction();
        inOrder.verify(mockApi).logMessage("Action performed successfully");
    }
}
