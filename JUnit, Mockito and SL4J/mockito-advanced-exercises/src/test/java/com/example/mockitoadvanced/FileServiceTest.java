package com.example.mockitoadvanced;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Exercise 3: Mocking File I/O
 * Mocks both the FileReader and FileWriter, stubs read() to simulate
 * file content, then verifies FileService.processFile() reads, processes,
 * and writes the content correctly.
 */
class FileServiceTest {

    @Test
    void testServiceWithMockFileIO() {
        // 1. Create a mock file reader and writer using Mockito.
        FileReader mockFileReader = mock(FileReader.class);
        FileWriter mockFileWriter = mock(FileWriter.class);

        // 2. Stub the file reader and writer methods to simulate file operations.
        when(mockFileReader.read()).thenReturn("Mock File Content");

        // 3. Write a test to verify the service logic using the mocked file reader and writer.
        FileService fileService = new FileService(mockFileReader, mockFileWriter);
        String result = fileService.processFile();

        assertEquals("Processed Mock File Content", result);

        // Bonus: confirm the processed content was actually written out.
        verify(mockFileWriter).write("Processed Mock File Content");
    }
}
