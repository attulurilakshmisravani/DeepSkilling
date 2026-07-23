package com.cognizant.springlearn.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown by CountryService.getCountry() when the requested country code
 * does not exist in the country list.
 *
 * The @ResponseStatus annotation instructs Spring to automatically
 * respond with HTTP 404 (Not Found) and the given reason message
 * whenever this exception propagates out of a controller method,
 * without needing any explicit exception handler.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Country not found")
public class CountryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CountryNotFoundException() {
        super("Country not found");
    }

    public CountryNotFoundException(String message) {
        super(message);
    }
}
