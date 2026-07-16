package com.bootcamp.ormdemo.repository;

import com.bootcamp.ormdemo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Traverses the Many-to-Many relationship: find every student enrolled
    // in a course whose title contains the given keyword.
    List<Student> findByCoursesTitleContainingIgnoreCase(String courseKeyword);
}
