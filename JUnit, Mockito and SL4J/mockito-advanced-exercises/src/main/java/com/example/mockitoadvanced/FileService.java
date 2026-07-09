package com.example.mockitoadvanced;

/**
 * Exercise 3: Mocking File I/O
 * Service under test — reads content via a FileReader, processes it,
 * and writes the processed content back out via a FileWriter.
 */
public class FileService {

    private final FileReader fileReader;
    private final FileWriter fileWriter;

    public FileService(FileReader fileReader, FileWriter fileWriter) {
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }

    public String processFile() {
        String content = fileReader.read();
        String processed = "Processed " + content;
        fileWriter.write(processed);
        return processed;
    }
}
