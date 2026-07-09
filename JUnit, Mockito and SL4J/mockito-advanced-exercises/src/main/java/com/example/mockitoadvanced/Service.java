package com.example.mockitoadvanced;

/**
 * Exercise 1: Mocking Databases and Repositories
 * Exercise 5: Mocking Multiple Return Values
 * Service under test — depends on a Repository to fetch raw data,
 * then applies some simple "processing" to it.
 */
public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public String processData() {
        return "Processed " + repository.getData();
    }
}
