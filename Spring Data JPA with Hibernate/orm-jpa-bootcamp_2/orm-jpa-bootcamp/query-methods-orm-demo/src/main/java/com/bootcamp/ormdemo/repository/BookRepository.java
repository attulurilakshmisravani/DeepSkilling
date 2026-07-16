package com.bootcamp.ormdemo.repository;

import com.bootcamp.ormdemo.model.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * This repository demonstrates the Spring Data JPA "Query Methods" feature:
 * queries are derived directly from the METHOD NAME - Spring parses the
 * name, matches it against the entity's properties, and builds the
 * equivalent JPQL query automatically. No JPQL/SQL is written by hand
 * for any of the methods below.
 *
 * Reference: docs.spring.io Spring Data JPA - Query Creation
 * (jpa.query-methods.query-creation)
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    // ---- 1. Search by CONTAINING text ----
    // -> WHERE UPPER(title) LIKE UPPER('%<keyword>%')
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    // ---- 2. Filter by STARTING text ----
    // -> WHERE title LIKE '<prefix>%'
    List<Book> findByTitleStartingWith(String prefix);

    // ---- 3. Sorting ----
    // "OrderBy...Asc/Desc" baked right into the method name
    List<Book> findByAuthorIdOrderByPublishedDateDesc(Long authorId);

    // Alternatively, sorting can be passed in dynamically via a Sort argument
    // instead of being hard-coded into the method name:
    List<Book> findByPriceGreaterThan(Double price, Sort sort);

    // ---- 4. Fetch BETWEEN dates ----
    // -> WHERE published_date BETWEEN <start> AND <end>
    List<Book> findByPublishedDateBetween(LocalDate startDate, LocalDate endDate);

    // ---- 5. GREATER THAN / LESS THAN ----
    List<Book> findByPriceGreaterThan(Double price);
    List<Book> findByPriceLessThan(Double price);
    List<Book> findByPriceGreaterThanEqual(Double price);
    List<Book> findByPublishedDateAfter(LocalDate date);
    List<Book> findByPublishedDateBefore(LocalDate date);

    // ---- 6. TOP / FIRST N results ----
    // -> the 3 cheapest books
    List<Book> findTop3ByOrderByPriceAsc();

    // -> the single most recently published book
    Book findFirstByOrderByPublishedDateDesc();

    // -> top 5 most expensive books by a given author (combines relationship
    //    traversal - Book.author.name - with Top + OrderBy)
    List<Book> findTop5ByAuthorNameOrderByPriceDesc(String authorName);
}
