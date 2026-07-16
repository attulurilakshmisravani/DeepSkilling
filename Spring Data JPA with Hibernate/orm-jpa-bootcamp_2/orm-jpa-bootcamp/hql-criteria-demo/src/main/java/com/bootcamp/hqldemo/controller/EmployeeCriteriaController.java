package com.bootcamp.hqldemo.controller;

import com.bootcamp.hqldemo.model.Employee;
import com.bootcamp.hqldemo.service.EmployeeCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Every endpoint here is backed by CriteriaBuilder / CriteriaQuery / Root /
 * TypedQuery in EmployeeCriteriaService - no @Query string anywhere.
 */
@RestController
@RequestMapping("/api/criteria")
public class EmployeeCriteriaController {

    private final EmployeeCriteriaService employeeCriteriaService;

    @Autowired
    public EmployeeCriteriaController(EmployeeCriteriaService employeeCriteriaService) {
        this.employeeCriteriaService = employeeCriteriaService;
    }

    // Dynamic multi-filter search - every query param is optional
    @GetMapping("/search")
    public List<Employee> search(
            @RequestParam(required = false) String nameContains,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hiredAfter) {
        return employeeCriteriaService.search(nameContains, department, minSalary, maxSalary, hiredAfter);
    }

    @GetMapping("/salary-summary-by-department")
    public List<Object[]> averageSalaryPerDepartment() {
        return employeeCriteriaService.averageSalaryPerDepartmentCriteria();
    }

    @GetMapping("/sorted")
    public List<Employee> sorted(@RequestParam(defaultValue = "name") String sortField,
                                  @RequestParam(defaultValue = "true") boolean ascending) {
        return employeeCriteriaService.findAllSorted(sortField, ascending);
    }
}
