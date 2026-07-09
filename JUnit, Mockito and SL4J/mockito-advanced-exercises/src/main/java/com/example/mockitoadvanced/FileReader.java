package com.example.mockitoadvanced;

/**
 * Exercise 3: Mocking File I/O
 * A small abstraction over reading file content, so FileService doesn't
 * depend directly on java.io classes and can be tested with a mock.
 *
 * Note: this is our own interface, distinct from java.io.FileReader.
 */
public interface FileReader {

    String read();
}
