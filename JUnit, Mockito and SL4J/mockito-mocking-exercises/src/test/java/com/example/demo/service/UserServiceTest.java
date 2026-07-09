package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Exercise 2: Mocking a Repository in a Service Test
 *
 * @ExtendWith(MockitoExtension.class) enables Mockito annotations without
 * starting any Spring context at all - this is the fastest kind of test.
 * @Mock creates a fake UserRepository, and @InjectMocks wires that fake
 * straight into a real UserService instance via reflection.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1L, "Sravani");
    }

    @Test
    @DisplayName("getUserById() should return the user when the repository finds one")
    void testGetUserById_found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        User result = userService.getUserById(1L);

        assertEquals("Sravani", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getUserById() should return null when the repository finds nothing")
    void testGetUserById_notFound() {
        when(userRepository.findById(42L)).thenReturn(Optional.empty());

        User result = userService.getUserById(42L);

        assertNull(result);
        verify(userRepository, times(1)).findById(42L);
    }

}
