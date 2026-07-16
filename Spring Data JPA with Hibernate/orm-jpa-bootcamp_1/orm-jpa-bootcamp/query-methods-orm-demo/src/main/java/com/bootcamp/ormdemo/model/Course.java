package com.bootcamp.ormdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Course is the INVERSE (non-owning) side of the Many-to-Many with Student.
 *
 * mappedBy = "courses" tells Hibernate: "don't create another join table for
 * this side - the STUDENT_COURSE join table is already fully described over
 * on Student.courses. Just mirror it here for convenient Course -> Students
 * navigation in Java."
 *
 * This mirrors exactly how @OneToMany(mappedBy = ...) works on Author above,
 * just applied to a Many-to-Many instead of a One-to-Many.
 */
@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    public Course() {
    }

    public Course(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
