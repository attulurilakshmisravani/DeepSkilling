package com.example.demo.integration;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Exercise 4: Integration Test with Spring Boot
 *
 * @SpringBootTest boots the FULL application context (controller, service,
 * repository, JPA, embedded H2 DB). AutoConfigureMockMvc lets us drive
 * real HTTP-style requests through the whole stack, no mocks involved.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Full flow: save a user via repository, then fetch it through the controller")
    void testFullFlow_saveThenGet() throws Exception {
        User saved = userRepository.save(new User(null, "Integration Sravani"));

        mockMvc.perform(get("/users/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("Integration Sravani"));
    }

    @Test
    @DisplayName("Full flow: POST a new user through the controller and verify it lands in the DB")
    void testFullFlow_createThroughController() throws Exception {
        User newUser = new User(null, "Created Via Controller");

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Created Via Controller"));

        assertThatUserWasPersisted("Created Via Controller");
    }

    private void assertThatUserWasPersisted(String name) {
        boolean found = userRepository.findByName(name).size() == 1;
        org.junit.jupiter.api.Assertions.assertTrue(found,
                "Expected exactly one persisted user named '" + name + "'");
    }

}
