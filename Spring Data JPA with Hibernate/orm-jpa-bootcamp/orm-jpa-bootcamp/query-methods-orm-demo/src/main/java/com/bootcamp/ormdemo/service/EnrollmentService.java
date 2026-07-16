package com.bootcamp.ormdemo.service;

import com.bootcamp.ormdemo.model.Course;
import com.bootcamp.ormdemo.model.Student;
import com.bootcamp.ormdemo.repository.CourseRepository;
import com.bootcamp.ormdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Student createStudent(String name) {
        return studentRepository.save(new Student(name));
    }

    @Transactional
    public Course createCourse(String title) {
        return courseRepository.save(new Course(title));
    }

    @Transactional
    public Student enroll(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("No student with id " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("No course with id " + courseId));
        student.enrollIn(course);
        return studentRepository.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public List<Student> findStudentsInCourse(String courseKeyword) {
        return studentRepository.findByCoursesTitleContainingIgnoreCase(courseKeyword);
    }

    public List<Course> findCoursesForStudent(String studentName) {
        return courseRepository.findByStudentsNameIgnoreCase(studentName);
    }
}
