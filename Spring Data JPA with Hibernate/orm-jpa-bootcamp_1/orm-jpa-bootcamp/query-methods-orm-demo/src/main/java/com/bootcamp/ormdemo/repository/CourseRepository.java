package com.bootcamp.ormdemo.repository;

import com.bootcamp.ormdemo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Traverses from the inverse side of the Many-to-Many: find every
    // course that a given student (by name) is enrolled in.
    List<Course> findByStudentsNameIgnoreCase(String studentName);
}
