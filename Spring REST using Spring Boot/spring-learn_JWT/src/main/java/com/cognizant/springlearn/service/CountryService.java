package com.cognizant.springlearn.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

@Service
public class CountryService {

    // Spring context built from the country.xml bean configuration file
    // located in src/main/resources. This is where the country list bean
    // ("countryList") is defined.
    private final ApplicationContext countryContext =
            new ClassPathXmlApplicationContext("country.xml");

    /**
     * Loads the full country list from country.xml
     */
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        return (List<Country>) countryContext.getBean("countryList");
    }

    /**
     * Returns the country matching the given code (case-insensitive).
     * Throws CountryNotFoundException if no match is found.
     *
     * Implemented using a lambda expression / Stream instead of a
     * manual for-loop, as suggested in the hands-on instructions.
     */
    public Country getCountry(String code) {
        return getAllCountries().stream()
                .filter(country -> country.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
    }

    /**
     * Convenience method used by the /country endpoint to return
     * India's details specifically.
     */
    public Country getCountryIndia() {
        return getCountry("IN");
    }
}
