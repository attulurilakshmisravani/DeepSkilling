package com.example.demo.integration;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Exercise 3: Mocking a Service Dependency in an Integration Test
 *
 * @SpringBootTest boots the FULL application context (unlike @WebMvcTest,
 * which only loads the web layer). @AutoConfigureMockMvc lets us drive real
 * HTTP-style requests into it. The twist here is @MockBean: even though the
 * whole context is up, UserService is replaced by a Mockito mock, so
 * UserRepository / the database are never actually touched - only the
 * controller -> service wiring is under test.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("GET /users/{id} should return the user provided by the mocked service, through the full context")
    void testGetUser_withMockedService() throws Exception {
        User mockUser = new User(1L, "Integration Sravani");
        when(userService.getUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Integration Sravani"));

        verify(userService).getUserById(1L);
    }

    @Test
    @DisplayName("GET /users/{id} should return 200 with empty body when the mocked service finds nothing")
    void testGetUser_withMockedServiceReturningNull() throws Exception {
        when(userService.getUserById(999L)).thenReturn(null);

        mockMvc.perform(get("/users/{id}", 999L))
                .andExpect(status().isOk());

        verify(userService).getUserById(999L);
    }

}
