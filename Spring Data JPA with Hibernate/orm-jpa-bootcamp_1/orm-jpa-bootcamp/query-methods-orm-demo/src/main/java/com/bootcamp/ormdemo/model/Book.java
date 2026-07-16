package com.bootcamp.ormdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * The "MANY" side of the relationship - many Books can belong to one Author.
 *
 * @ManyToOne marks this as the owning side of the relationship (the side
 *   that holds the foreign key).
 * @JoinColumn(name = "author_id") explicitly names the foreign key column
 *   that gets created in the BOOK table, pointing back at AUTHOR.id.
 * FetchType.LAZY means the related Author is NOT loaded from the database
 *   until book.getAuthor() is actually called - avoids an extra join/query
 *   on every single Book read when you don't need the author's details.
 *   (Contrast this with FetchType.EAGER used on the Course<->Student
 *   many-to-many below, where the related side IS always loaded immediately.)
 */
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Double price;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() {
    }

    public Book(String title, Double price, LocalDate publishedDate, Author author) {
        this.title = title;
        this.price = price;
        this.publishedDate = publishedDate;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
