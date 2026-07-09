package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Exercise 1: Mocking a Service Dependency in a Controller Test
 *
 * @WebMvcTest loads only the web layer (UserController + Spring MVC
 * infrastructure) - it does NOT start the real UserService, UserRepository,
 * or a database. @MockBean swaps in a Mockito mock of UserService, which we
 * then stub with when(...).thenReturn(...) to control exactly what the
 * controller sees.
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("GET /users/{id} should return the user supplied by the mocked service")
    void testGetUser_returnsUserFromMockedService() throws Exception {
        User mockUser = new User(1L, "Sravani");
        when(userService.getUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sravani"));

        verify(userService).getUserById(1L);
    }

    @Test
    @DisplayName("GET /users/{id} should return an empty body when the mocked service returns null")
    void testGetUser_returnsNullBodyWhenServiceReturnsNull() throws Exception {
        when(userService.getUserById(99L)).thenReturn(null);

        mockMvc.perform(get("/users/{id}", 99L))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(userService).getUserById(99L);
    }

}
