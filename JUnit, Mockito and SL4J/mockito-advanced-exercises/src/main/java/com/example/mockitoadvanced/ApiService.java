package com.example.mockitoadvanced;

/**
 * Exercise 2: Mocking External Services (RESTful APIs)
 * Service under test — depends on a RestClient to call an external API.
 */
public class ApiService {

    private final RestClient restClient;

    public ApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String fetchData() {
        return "Fetched " + restClient.getResponse();
    }
}
