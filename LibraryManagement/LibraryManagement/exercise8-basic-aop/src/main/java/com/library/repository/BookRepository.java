package com.library.repository;

public class BookRepository {
    public String findBookById(int id) {
        return "Book #" + id + ": The Spring Framework Handbook";
    }
}
