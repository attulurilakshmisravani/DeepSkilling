package com.bootcamp.hqldemo.controller;

import com.bootcamp.hqldemo.dto.DepartmentSalarySummary;
import com.bootcamp.hqldemo.model.Employee;
import com.bootcamp.hqldemo.service.EmployeeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Every endpoint maps to an @Query method on EmployeeRepository - either
 * HQL/JPQL (default) or nativeQuery = true (raw SQL).
 */
@RestController
@RequestMapping("/api/hql")
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;

    @Autowired
    public EmployeeQueryController(EmployeeQueryService employeeQueryService) {
        this.employeeQueryService = employeeQueryService;
    }

    // --- HQL / JPQL ---

    @GetMapping("/high-earners")
    public List<Employee> highEarners(@RequestParam Double minSalary) {
        return employeeQueryService.highEarners(minSalary);
    }

    @GetMapping("/search")
    public List<Employee> search(@RequestParam String name) {
        return employeeQueryService.searchByName(name);
    }

    // Demonstrates the "fetch" keyword (JOIN FETCH) avoiding N+1 lazy loads
    @GetMapping("/department/{deptName}/with-fetch")
    public List<Employee> byDepartmentWithFetch(@PathVariable String deptName) {
        return employeeQueryService.byDepartmentWithFetch(deptName);
    }

    // Aggregate functions: AVG + COUNT + GROUP BY
    @GetMapping("/salary-summary-by-department")
    public List<DepartmentSalarySummary> averageSalaryByDepartment() {
        return employeeQueryService.averageSalaryByDepartment();
    }

    // Aggregate functions: MAX / MIN
    @GetMapping("/salary-stats")
    public Map<String, Double> salaryStats() {
        return employeeQueryService.salaryStats();
    }

    // Aggregate function: SUM, filtered
    @GetMapping("/department/{deptName}/total-salary")
    public Double totalSalaryForDepartment(@PathVariable String deptName) {
        return employeeQueryService.totalSalaryForDepartment(deptName);
    }

    // Bulk UPDATE written directly in HQL/JPQL
    @PostMapping("/department/{deptName}/give-raise")
    public String giveRaise(@PathVariable String deptName, @RequestParam Double percent) {
        int updated = employeeQueryService.giveRaise(deptName, percent);
        return updated + " employee(s) in " + deptName + " received a " + percent + "% raise";
    }

    // --- Native Query (nativeQuery = true) ---

    @GetMapping("/native/salary-range")
    public List<Employee> bySalaryRangeNative(@RequestParam Double min, @RequestParam Double max) {
        return employeeQueryService.bySalaryRangeNative(min, max);
    }

    @GetMapping("/native/department/{deptName}")
    public List<Employee> byDepartmentNative(@PathVariable String deptName) {
        return employeeQueryService.byDepartmentNative(deptName);
    }

    @GetMapping("/native/tenure")
    public List<Employee> withTenureNative(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate asOf,
            @RequestParam int years) {
        return employeeQueryService.withTenureNative(asOf, years);
    }
}
