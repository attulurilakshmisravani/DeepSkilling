package com.bootcamp.ormdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Student is the OWNING side of a Many-to-Many relationship with Course.
 *
 * @JoinTable creates a separate join/junction table (STUDENT_COURSE) with
 *   two foreign key columns:
 *     - joinColumns: the FK pointing back at THIS entity (student_id)
 *     - inverseJoinColumns: the FK pointing at the OTHER entity (course_id)
 *
 * FetchType.EAGER here means whenever a Student is loaded, their full set of
 * Courses is loaded immediately in the same query context - illustrating the
 * opposite choice from the LAZY Book -> Author relationship above. EAGER is
 * convenient but can hurt performance on large graphs, so it's a deliberate
 * choice here just to demonstrate the contrast.
 */
@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "STUDENT_COURSE",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void enrollIn(Course course) {
        courses.add(course);
        course.getStudents().add(this);
    }
}
