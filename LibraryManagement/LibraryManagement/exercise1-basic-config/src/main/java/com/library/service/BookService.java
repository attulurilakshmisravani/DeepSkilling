package com.library.service;

public class BookService {

    public BookService() {
        System.out.println("BookService bean created.");
    }

    public String getBookInfo(int id) {
        return "Fetching info for book id " + id;
    }
}
