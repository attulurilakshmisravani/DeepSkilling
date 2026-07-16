package com.bootcamp.ormdemo.controller;

import com.bootcamp.ormdemo.model.Course;
import com.bootcamp.ormdemo.model.Student;
import com.bootcamp.ormdemo.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Demonstrates the Many-to-Many O/R mapping between Student and Course
 * (@ManyToMany, @JoinTable, mappedBy) declared in the Student/Course entities.
 */
@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/students")
    public Student createStudent(@RequestParam String name) {
        return enrollmentService.createStudent(name);
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestParam String title) {
        return enrollmentService.createCourse(title);
    }

    @PostMapping("/students/{studentId}/enroll/{courseId}")
    public Student enroll(@PathVariable Long studentId, @PathVariable Long courseId) {
        return enrollmentService.enroll(studentId, courseId);
    }

    @GetMapping("/students")
    public List<Student> allStudents() {
        return enrollmentService.findAllStudents();
    }

    @GetMapping("/courses")
    public List<Course> allCourses() {
        return enrollmentService.findAllCourses();
    }

    // Query method traversing the many-to-many: students in a given course
    @GetMapping("/courses/{keyword}/students")
    public List<Student> studentsInCourse(@PathVariable String keyword) {
        return enrollmentService.findStudentsInCourse(keyword);
    }

    // Query method traversing the many-to-many the other way: courses for a student
    @GetMapping("/students/{name}/courses")
    public List<Course> coursesForStudent(@PathVariable String name) {
        return enrollmentService.findCoursesForStudent(name);
    }
}
