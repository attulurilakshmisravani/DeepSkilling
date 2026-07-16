package com.bootcamp.ormdemo.repository;

import com.bootcamp.ormdemo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Search by containing text (case-insensitive)
    List<Author> findByNameContainingIgnoreCase(String namePart);
}
