package com.example.mockitoadvanced;

/**
 * Exercise 4: Mocking Network Interactions
 * Service under test — depends on a NetworkClient to connect to a server.
 */
public class NetworkService {

    private final NetworkClient networkClient;

    public NetworkService(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public String connectToServer() {
        return "Connected to " + networkClient.connect();
    }
}
