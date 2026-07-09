package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Exercise 3: Testing a REST Controller with MockMvc
 * Exercise 5: Test Controller POST Endpoint
 * Exercise 8: Test Controller Exception Handling
 *
 * @WebMvcTest loads only the web layer (controller + advice), not the
 * whole application context, and mocks UserService with @MockBean.
 */
@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- Exercise 3: GET ----------

    @Test
    @DisplayName("GET /users/{id} should return the user as JSON")
    void testGetUser() throws Exception {
        User user = new User(1L, "Sravani");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sravani"));
    }

    // ---------- Exercise 5: POST ----------

    @Test
    @DisplayName("POST /users should create a user and return it as JSON")
    void testCreateUser() throws Exception {
        User requestUser = new User(null, "New User");
        User savedUser = new User(10L, "New User");

        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("New User"));
    }

    // ---------- Exercise 8: Controller exception handling ----------

    @Test
    @DisplayName("GET /users/strict/{id} should return 404 with message when user is missing")
    void testGetUserStrict_notFound() throws Exception {
        when(userService.getUserByIdOrThrow(eq(404L)))
                .thenThrow(new UserNotFoundException(404L));

        mockMvc.perform(get("/users/strict/{id}", 404L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

}
