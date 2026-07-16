package com.bootcamp.ormdemo.controller;

import com.bootcamp.ormdemo.model.Author;
import com.bootcamp.ormdemo.model.Book;
import com.bootcamp.ormdemo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Every endpoint here maps to one of the Query Methods declared on
 * BookRepository / AuthorRepository - see LibraryService for the wiring.
 */
@RestController
@RequestMapping("/api/library")
public class BookController {

    private final LibraryService libraryService;

    @Autowired
    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/authors")
    public Author createAuthor(@RequestParam String name) {
        return libraryService.createAuthor(name);
    }

    @GetMapping("/authors")
    public List<Author> allAuthors() {
        return libraryService.findAllAuthors();
    }

    // Query method: CONTAINING text
    @GetMapping("/authors/search")
    public List<Author> searchAuthors(@RequestParam String name) {
        return libraryService.searchAuthorsByName(name);
    }

    @PostMapping("/authors/{authorId}/books")
    public Book addBook(@PathVariable Long authorId,
                         @RequestParam String title,
                         @RequestParam Double price,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishedDate) {
        return libraryService.addBookToAuthor(authorId, title, price, publishedDate);
    }

    @GetMapping("/books")
    public List<Book> allBooks() {
        return libraryService.findAllBooks();
    }

    // Query method: CONTAINING text
    @GetMapping("/books/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return libraryService.searchBooksByTitle(keyword);
    }

    // Query method: STARTING WITH
    @GetMapping("/books/starting-with/{prefix}")
    public List<Book> booksStartingWith(@PathVariable String prefix) {
        return libraryService.findBooksStartingWith(prefix);
    }

    // Query method: sorting baked into the method name (OrderBy...Desc)
    @GetMapping("/authors/{authorId}/books/by-date")
    public List<Book> booksByAuthorSortedByDate(@PathVariable Long authorId) {
        return libraryService.findBooksByAuthorSortedByDate(authorId);
    }

    // Query methods: GREATER THAN / LESS THAN
    @GetMapping("/books/price")
    public List<Book> booksByPrice(@RequestParam Double value,
                                    @RequestParam(defaultValue = "false") boolean cheaper) {
        return libraryService.findBooksCheaperOrPricierThan(value, cheaper);
    }

    // Query method: BETWEEN dates
    @GetMapping("/books/published-between")
    public List<Book> booksPublishedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return libraryService.findBooksPublishedBetween(start, end);
    }

    // Query method: TOP N
    @GetMapping("/books/cheapest")
    public List<Book> cheapestBooks() {
        return libraryService.findCheapestBooks();
    }

    // Query method: FIRST (top 1)
    @GetMapping("/books/most-recent")
    public Book mostRecentBook() {
        return libraryService.findMostRecentBook();
    }

    // Query method: TOP N + relationship traversal (Book.author.name) + sorting
    @GetMapping("/books/top-by-author")
    public List<Book> topBooksByAuthor(@RequestParam String authorName) {
        return libraryService.findTopBooksByAuthor(authorName);
    }

    // Query method with a dynamic Sort parameter instead of a hard-coded OrderBy
    @GetMapping("/books/price-greater-than-sorted")
    public List<Book> booksPriceGreaterThanSorted(@RequestParam Double price,
                                                   @RequestParam(defaultValue = "price") String sortBy,
                                                   @RequestParam(defaultValue = "true") boolean ascending) {
        return libraryService.findBooksAboveDynamicallySorted(price, sortBy, ascending);
    }
}
