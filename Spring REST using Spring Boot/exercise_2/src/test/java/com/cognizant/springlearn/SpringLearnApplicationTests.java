package com.cognizant.springlearn;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.cognizant.springlearn.controller.CountryController;

@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    @Autowired
    private CountryController countryController;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test that the CountryController bean is loaded correctly
     * by the Spring application context.
     */
    @Test
    public void contextLoads() {
        assertNotNull(countryController);
    }

    /**
     * Invoke GET /country and verify the response contains
     * code "IN" and name "India".
     */
    @Test
    public void getCountryIndia_shouldReturnIndiaDetails() throws Exception {
        mockMvc.perform(get("/country"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code").value("IN"))
                .andExpect(jsonPath("$.name").value("India"));
    }

    /**
     * Invoke GET /countries/{code} with a valid, mixed-case code
     * and verify the case-insensitive match works.
     */
    @Test
    public void getCountry_withValidCode_shouldReturnCountry() throws Exception {
        mockMvc.perform(get("/countries/in"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("IN"))
                .andExpect(jsonPath("$.name").value("India"));
    }

    /**
     * Invoke GET /countries/{code} with an unknown code and verify
     * that a 404 Bad Request / Not Found response is returned.
     */
    @Test
    public void getCountry_withInvalidCode_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/countries/az"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Country not found"));
    }
}
