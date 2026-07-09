package com.example.demo.repository;

import com.example.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Exercise 7: Test Custom Repository Query
 *
 * @DataJpaTest spins up an in-memory H2 database and only the JPA layer,
 * so findByName() is exercised against a real (embedded) database.
 */
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRepository.save(new User(null, "Sravani"));
        userRepository.save(new User(null, "Sravani"));
        userRepository.save(new User(null, "Ramesh"));
    }

    @Test
    @DisplayName("findByName() should return all users matching the given name")
    void testFindByName() {
        List<User> results = userRepository.findByName("Sravani");

        assertEquals(2, results.size());
        results.forEach(u -> assertEquals("Sravani", u.getName()));
    }

    @Test
    @DisplayName("findByName() should return an empty list when no match exists")
    void testFindByName_noMatch() {
        List<User> results = userRepository.findByName("Nobody");

        assertEquals(0, results.size());
    }

}
