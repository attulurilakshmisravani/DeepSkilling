package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Exercise 2: Mocking a Repository in a Service Test
 * Exercise 6: Test Service Exception Handling
 * Exercise 7: Test Custom Repository Query
 *
 * Pure Mockito unit test - no Spring context is started, which keeps
 * the test fast. UserRepository is mocked and injected into UserService.
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

    // ---------- Exercise 2 ----------

    @Test
    @DisplayName("getUserById() should return the user when found")
    void testGetUserById_found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Sravani", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getUserById() should return null when user is not found")
    void testGetUserById_notFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        User result = userService.getUserById(99L);

        assertNull(result);
        verify(userRepository).findById(99L);
    }

    // ---------- Exercise 6: Exception Handling ----------

    @Test
    @DisplayName("getUserByIdOrThrow() should throw UserNotFoundException when missing")
    void testGetUserByIdOrThrow_throwsWhenMissing() {
        when(userRepository.findById(42L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserByIdOrThrow(42L)
        );

        assertTrue(exception.getMessage().contains("42"));
        verify(userRepository).findById(42L);
    }

    @Test
    @DisplayName("getUserByIdOrThrow() should return the user when present")
    void testGetUserByIdOrThrow_returnsUserWhenPresent() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        User result = userService.getUserByIdOrThrow(1L);

        assertEquals(sampleUser, result);
    }

    // ---------- Exercise 7: Custom Repository Query ----------

    @Test
    @DisplayName("getUsersByName() should delegate to repository.findByName()")
    void testGetUsersByName() {
        User secondUser = new User(2L, "Sravani");
        when(userRepository.findByName("Sravani"))
                .thenReturn(List.of(sampleUser, secondUser));

        List<User> results = userService.getUsersByName("Sravani");

        assertEquals(2, results.size());
        verify(userRepository, times(1)).findByName("Sravani");
    }

}
