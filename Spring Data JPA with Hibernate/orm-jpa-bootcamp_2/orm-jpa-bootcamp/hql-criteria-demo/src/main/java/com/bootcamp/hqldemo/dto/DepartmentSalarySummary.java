package com.bootcamp.hqldemo.dto;

/**
 * Simple projection used for the "aggregate functions in HQL" example:
 * SELECT department name, AVG(salary) ... GROUP BY department name.
 *
 * Built via a JPQL "constructor expression" (new com.bootcamp...DepartmentSalarySummary(...))
 * directly inside the @Query - this is the standard, type-safe way to
 * return aggregate/projection results from HQL/JPQL instead of a raw Object[].
 */
public class DepartmentSalarySummary {

    private final String departmentName;
    private final Double averageSalary;
    private final Long employeeCount;

    public DepartmentSalarySummary(String departmentName, Double averageSalary, Long employeeCount) {
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
        this.employeeCount = employeeCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public Long getEmployeeCount() {
        return employeeCount;
    }
}
