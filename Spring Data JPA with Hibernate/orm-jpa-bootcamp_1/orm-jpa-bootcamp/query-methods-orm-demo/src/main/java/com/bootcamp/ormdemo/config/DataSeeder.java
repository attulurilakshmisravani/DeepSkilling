package com.bootcamp.ormdemo.config;

import com.bootcamp.ormdemo.model.Author;
import com.bootcamp.ormdemo.model.Book;
import com.bootcamp.ormdemo.model.Course;
import com.bootcamp.ormdemo.model.Student;
import com.bootcamp.ormdemo.repository.AuthorRepository;
import com.bootcamp.ormdemo.repository.CourseRepository;
import com.bootcamp.ormdemo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Seeds some sample data on startup purely so the query-method endpoints
 * have something to return immediately (curl-able right away, no manual
 * POSTing required first).
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public DataSeeder(AuthorRepository authorRepository,
                       StudentRepository studentRepository,
                       CourseRepository courseRepository) {
        this.authorRepository = authorRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) {
        // --- Authors + Books (One-to-Many / Many-to-One) ---
        Author rowling = new Author("J.K. Rowling");
        rowling.addBook(new Book("Harry Potter and the Philosopher's Stone", 599.0, LocalDate.of(1997, 6, 26), rowling));
        rowling.addBook(new Book("Harry Potter and the Chamber of Secrets", 649.0, LocalDate.of(1998, 7, 2), rowling));
        authorRepository.save(rowling);

        Author tolkien = new Author("J.R.R. Tolkien");
        tolkien.addBook(new Book("The Hobbit", 499.0, LocalDate.of(1937, 9, 21), tolkien));
        tolkien.addBook(new Book("The Fellowship of the Ring", 799.0, LocalDate.of(1954, 7, 29), tolkien));
        tolkien.addBook(new Book("The Two Towers", 799.0, LocalDate.of(1954, 11, 11), tolkien));
        authorRepository.save(tolkien);

        // --- Students + Courses (Many-to-Many) ---
        Student alice = studentRepository.save(new Student("Alice"));
        Student bob = studentRepository.save(new Student("Bob"));

        Course dataStructures = courseRepository.save(new Course("Data Structures"));
        Course spring = courseRepository.save(new Course("Spring Framework"));

        alice.enrollIn(dataStructures);
        alice.enrollIn(spring);
        bob.enrollIn(spring);

        studentRepository.save(alice);
        studentRepository.save(bob);
    }
}
