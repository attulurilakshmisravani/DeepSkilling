package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

@RestController
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    /**
     * REST - Country Web Service
     * URL: /country
     * Loads India bean from spring xml configuration (via CountryService) and returns it.
     * Sample Response: { "code": "IN", "name": "India" }
     */
    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public Country getCountryIndia() {
        logger.info("START - getCountryIndia()");

        Country india = countryService.getCountryIndia();

        logger.info("END - getCountryIndia()");
        return india;
    }

    /**
     * REST - Get all countries
     * URL: /countries
     * Sample Response:
     * [
     *   { "code": "IN", "name": "India" },
     *   { "code": "US", "name": "United States" },
     *   { "code": "JP", "name": "Japan" },
     *   { "code": "DE", "name": "Germany" }
     * ]
     */
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        logger.info("START - getAllCountries()");

        List<Country> countries = countryService.getAllCountries();

        logger.info("END - getAllCountries()");
        return countries;
    }

    /**
     * REST - Get country based on country code
     * URL: /countries/{code}
     * The country code match is case-insensitive (see CountryService.getCountry()).
     * Throws CountryNotFoundException (-> HTTP 404) if the code does not exist.
     */
    @GetMapping("/countries/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        logger.info("START - getCountry() - code: {}", code);

        Country country = countryService.getCountry(code);

        logger.info("END - getCountry()");
        return country;
    }
}
