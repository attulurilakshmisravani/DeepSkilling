package com.example.mockito;

/**
 * Service under test throughout all the Mockito exercises.
 * It depends on ExternalApi, which we mock in the tests so we
 * never need to hit a real external system.
 */
public class MyService {

    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    /** Exercise 1 & 2: simple pass-through call. */
    public String fetchData() {
        return externalApi.getData();
    }

    /** Exercise 3: call that takes an argument, useful for argument matchers. */
    public String fetchDataById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id must not be null or empty");
        }
        return externalApi.getDataById(id);
    }

    /** Exercise 4 & 7: delegates to a void method. */
    public void saveData(String data) {
        externalApi.saveData(data);
    }

    /** Exercise 5: calls getData() multiple times, e.g. for polling/retrying. */
    public String fetchDataThreeTimes() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            result.append(externalApi.getData());
            if (i < 2) {
                result.append(",");
            }
        }
        return result.toString();
    }

    /** Exercise 6: calls two void methods in a specific order. */
    public void processData() {
        externalApi.performAction();
        externalApi.logMessage("Action performed successfully");
    }
}
