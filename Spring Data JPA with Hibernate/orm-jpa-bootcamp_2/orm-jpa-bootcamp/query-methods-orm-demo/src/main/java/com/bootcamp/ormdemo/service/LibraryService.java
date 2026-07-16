package com.bootcamp.ormdemo.service;

import com.bootcamp.ormdemo.model.Author;
import com.bootcamp.ormdemo.model.Book;
import com.bootcamp.ormdemo.repository.AuthorRepository;
import com.bootcamp.ormdemo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Author createAuthor(String name) {
        return authorRepository.save(new Author(name));
    }

    @Transactional
    public Book addBookToAuthor(Long authorId, String title, Double price, LocalDate publishedDate) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("No author with id " + authorId));
        Book book = new Book(title, price, publishedDate, author);
        return bookRepository.save(book);
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> searchAuthorsByName(String namePart) {
        return authorRepository.findByNameContainingIgnoreCase(namePart);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooksByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Book> findBooksStartingWith(String prefix) {
        return bookRepository.findByTitleStartingWith(prefix);
    }

    public List<Book> findBooksByAuthorSortedByDate(Long authorId) {
        return bookRepository.findByAuthorIdOrderByPublishedDateDesc(authorId);
    }

    public List<Book> findBooksCheaperOrPricierThan(Double price, boolean cheaper) {
        return cheaper ? bookRepository.findByPriceLessThan(price)
                        : bookRepository.findByPriceGreaterThan(price);
    }

    public List<Book> findBooksPublishedBetween(LocalDate start, LocalDate end) {
        return bookRepository.findByPublishedDateBetween(start, end);
    }

    public List<Book> findCheapestBooks() {
        return bookRepository.findTop3ByOrderByPriceAsc();
    }

    public Book findMostRecentBook() {
        return bookRepository.findFirstByOrderByPublishedDateDesc();
    }

    public List<Book> findTopBooksByAuthor(String authorName) {
        return bookRepository.findTop5ByAuthorNameOrderByPriceDesc(authorName);
    }

    public List<Book> findBooksAboveDynamicallySorted(Double price, String sortField, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return bookRepository.findByPriceGreaterThan(price, sort);
    }
}
