package com.bootcamp.ormdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * The "ONE" side of a One-to-Many relationship with Book.
 *
 * mappedBy = "author" tells Hibernate that the Book entity's "author" field
 * (annotated with @ManyToOne / @JoinColumn) already owns the relationship -
 * i.e. the foreign key column lives in the BOOK table, not here. Author does
 * NOT get its own join column; it just mirrors the relationship for
 * convenience when navigating from Author -> its Books in Java code.
 *
 * FetchType.LAZY is used here (and is the JPA default for collections) so
 * that loading an Author does NOT automatically pull every one of their
 * Books from the database - the books list is only fetched when you actually
 * access it. This avoids loading large object graphs you don't need.
 */
@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore // prevents infinite recursion when serializing Author -> Books -> Author -> ...
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.ALL })
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }
}
