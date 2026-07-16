package com.bootcamp.hqldemo.config;

import com.bootcamp.hqldemo.model.Department;
import com.bootcamp.hqldemo.model.Employee;
import com.bootcamp.hqldemo.repository.DepartmentRepository;
import com.bootcamp.hqldemo.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DataSeeder(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        Department engineering = departmentRepository.save(new Department("Engineering"));
        Department sales = departmentRepository.save(new Department("Sales"));

        employeeRepository.save(new Employee("Sravani", 95000.0, LocalDate.of(2019, 3, 15), engineering));
        employeeRepository.save(new Employee("Arjun", 88000.0, LocalDate.of(2021, 6, 1), engineering));
        employeeRepository.save(new Employee("Priya", 76000.0, LocalDate.of(2022, 1, 10), engineering));
        employeeRepository.save(new Employee("Rahul", 65000.0, LocalDate.of(2023, 8, 20), sales));
        employeeRepository.save(new Employee("Divya", 71000.0, LocalDate.of(2020, 11, 5), sales));
    }
}
