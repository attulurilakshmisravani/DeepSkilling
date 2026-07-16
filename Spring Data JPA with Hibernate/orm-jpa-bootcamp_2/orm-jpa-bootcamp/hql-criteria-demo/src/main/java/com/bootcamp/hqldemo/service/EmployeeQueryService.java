package com.bootcamp.hqldemo.service;

import com.bootcamp.hqldemo.dto.DepartmentSalarySummary;
import com.bootcamp.hqldemo.model.Employee;
import com.bootcamp.hqldemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeQueryService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeQueryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> highEarners(Double minSalary) {
        return employeeRepository.findHighEarners(minSalary);
    }

    public List<Employee> searchByName(String namePart) {
        return employeeRepository.searchByNameLike(namePart);
    }

    public List<Employee> byDepartmentWithFetch(String deptName) {
        return employeeRepository.findByDepartmentNameWithFetch(deptName);
    }

    public List<DepartmentSalarySummary> averageSalaryByDepartment() {
        return employeeRepository.averageSalaryByDepartment();
    }

    public Map<String, Double> salaryStats() {
        return Map.of(
                "max", employeeRepository.findMaxSalary(),
                "min", employeeRepository.findMinSalary()
        );
    }

    public Double totalSalaryForDepartment(String deptName) {
        return employeeRepository.totalSalaryForDepartment(deptName);
    }

    @Transactional
    public int giveRaise(String deptName, Double percent) {
        return employeeRepository.giveRaiseToDepartment(deptName, percent);
    }

    public List<Employee> bySalaryRangeNative(Double min, Double max) {
        return employeeRepository.findBySalaryRangeNative(min, max);
    }

    public List<Employee> byDepartmentNative(String deptName) {
        return employeeRepository.findByDepartmentNameNative(deptName);
    }

    public List<Employee> withTenureNative(LocalDate asOf, int years) {
        return employeeRepository.findEmployeesWithTenureNative(asOf, years);
    }
}
