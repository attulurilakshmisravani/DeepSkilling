package com.cognizant.springlearn.model;

/**
 * Simple POJO representing a country.
 * Jackson (used internally by Spring Web) automatically converts
 * an instance of this bean into the JSON response, e.g.:
 * { "code": "IN", "name": "India" }
 */
public class Country {

    private String code;
    private String name;

    // A no-arg constructor is required so this bean can be created
    // via Spring XML configuration (country.xml) and by Jackson.
    public Country() {
    }

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" + "code='" + code + '\'' + ", name='" + name + '\'' + '}';
    }
}
