package com.cognizant.springlearn;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
     * Invoke GET /country (authenticated as "user") and verify the
     * response contains code "IN" and name "India".
     */
    @Test
    public void getCountryIndia_shouldReturnIndiaDetails() throws Exception {
        mockMvc.perform(get("/country").with(httpBasic("user", "pwd")))
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
        mockMvc.perform(get("/countries/in").with(httpBasic("user", "pwd")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("IN"))
                .andExpect(jsonPath("$.name").value("India"));
    }

    /**
     * Invoke GET /countries/{code} with an unknown code and verify
     * that a 404 Not Found response is returned.
     */
    @Test
    public void getCountry_withInvalidCode_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/countries/az").with(httpBasic("user", "pwd")))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Country not found"));
    }

    /**
     * GET /countries without any credentials should be rejected
     * with 401 Unauthorized, since Spring Security now protects all endpoints.
     */
    @Test
    public void getCountries_withoutCredentials_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/countries"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * GET /countries with the correct role (USER) should succeed.
     */
    @Test
    public void getCountries_withUserRole_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/countries").with(httpBasic("user", "pwd")))
                .andExpect(status().isOk());
    }

    /**
     * GET /countries with any authenticated user (ADMIN) should succeed too.
     * NOTE: in SecurityConfig, the earlier role-specific rule
     * (.antMatchers("/countries").hasRole("USER")) is commented out once the
     * JWT filter stage is reached, per the hands-on instructions -- at that
     * point /countries just requires any authenticated user via
     * .anyRequest().authenticated(). Uncomment that line in SecurityConfig
     * if you want to go back to restricting /countries to role USER only.
     */
    @Test
    public void getCountries_withAdminRole_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/countries").with(httpBasic("admin", "pwd")))
                .andExpect(status().isOk());
    }

    /**
     * GET /authenticate with valid Basic credentials should return a JWT token
     * in the response body.
     */
    @Test
    public void authenticate_withValidCredentials_shouldReturnToken() throws Exception {
        mockMvc.perform(get("/authenticate").with(httpBasic("user", "pwd")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
