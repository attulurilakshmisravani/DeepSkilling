package com.library.service;

import com.library.repository.BookRepository;

public class BookService {

    private BookRepository bookRepository;

    // Setter method used by Spring to inject BookRepository (Setter Injection)
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookRepository injected into BookService.");
    }

    public String getBookInfo(int id) {
        return bookRepository.findBookById(id);
    }
}
