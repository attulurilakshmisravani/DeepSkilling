package com.bootcamp.hibernatexml;

/**
 * Plain persistence POJO. Notice there are NO annotations here at all -
 * the mapping to the "student" table lives entirely in Student.hbm.xml.
 * This is the classic "Hibernate XML Configuration" style.
 */
public class Student {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public Student() {
        // Hibernate requires a no-arg constructor
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName
                + "', email='" + email + "'}";
    }
}
