package com.library.service;

import com.library.repository.BookRepository;

public class BookService {

    private BookRepository bookRepository;   // set via constructor injection
    private String libraryName;               // set via setter injection

    // Constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookRepository injected via constructor.");
    }

    // Setter injection
    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
        System.out.println("libraryName injected via setter: " + libraryName);
    }

    public String getBookInfo(int id) {
        return "[" + libraryName + "] " + bookRepository.findBookById(id);
    }
}
