package com.bootcamp.hqldemo.service;

import com.bootcamp.hqldemo.model.Department;
import com.bootcamp.hqldemo.model.Employee;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * WHY CRITERIA QUERY? (see Oracle Java EE tutorial, "Using the Criteria API
 * to Create Queries")
 *
 * HQL/JPQL queries are STRINGS. That's fine for fixed, known-shape queries,
 * but falls apart for "dynamic search" screens where the set of filters
 * actually applied depends on what the user filled in - e.g. a search form
 * where name/department/min-salary/max-salary/hire-date-range are all
 * OPTIONAL and any combination might be present.
 *
 * Building that with string concatenation is error-prone (SQL injection
 * risk if done carelessly, easy to get parameter binding wrong, ugly to
 * read/maintain). The Criteria API instead lets you build the query as
 * JAVA OBJECTS - so:
 *   - it's type-safe (typos in field names are compile errors, not runtime
 *     surprises - especially with the JPA "metamodel" generator, not shown
 *     here to keep the demo dependency-free, but mentioned in the README)
 *   - predicates can be added/skipped conditionally with plain "if" checks
 *   - the whole query can be composed and reused across methods
 *
 * Core objects used below:
 *   - CriteriaBuilder: factory for constructing criteria queries, predicates
 *     (equal, like, between, greaterThan...), and expressions (avg, count...).
 *   - CriteriaQuery<T>: the query object itself - what to select, from where,
 *     with what predicates/ordering/grouping.
 *   - Root<T>: represents the FROM clause - the "e" in "from Employee e".
 *     Used to reference columns: root.get("salary"), root.get("name"), etc.
 *   - TypedQuery<T>: the final, executable, type-safe query object obtained
 *     from CriteriaQuery via entityManager.createQuery(criteriaQuery).
 */
@Service
public class EmployeeCriteriaService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Dynamic multi-filter search: every parameter is OPTIONAL. This is
     * exactly the scenario Criteria Query is built for - a JPQL string
     * version of this would need a different query for every combination
     * of filters actually supplied, or ugly string-building by hand.
     */
    public List<Employee> search(String nameContains,
                                  String departmentName,
                                  Double minSalary,
                                  Double maxSalary,
                                  LocalDate hiredAfter) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);

        // Join to Department only if we actually need to filter/sort by it
        Join<Employee, Department> department = employee.join("department");

        List<Predicate> predicates = new ArrayList<>();

        if (nameContains != null && !nameContains.isBlank()) {
            predicates.add(cb.like(cb.lower(employee.get("name")), "%" + nameContains.toLowerCase() + "%"));
        }
        if (departmentName != null && !departmentName.isBlank()) {
            predicates.add(cb.equal(department.get("name"), departmentName));
        }
        if (minSalary != null) {
            predicates.add(cb.greaterThanOrEqualTo(employee.get("salary"), minSalary));
        }
        if (maxSalary != null) {
            predicates.add(cb.lessThanOrEqualTo(employee.get("salary"), maxSalary));
        }
        if (hiredAfter != null) {
            predicates.add(cb.greaterThan(employee.get("hireDate"), hiredAfter));
        }

        query.select(employee)
             .where(cb.and(predicates.toArray(new Predicate[0])))
             .orderBy(cb.asc(employee.get("name")));

        TypedQuery<Employee> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    /**
     * Aggregate query built with Criteria instead of HQL: average salary
     * per department, using CriteriaBuilder.avg() + groupBy() - the
     * Criteria-API equivalent of the HQL aggregate query in EmployeeRepository.
     */
    public List<Object[]> averageSalaryPerDepartmentCriteria() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, Department> department = employee.join("department");

        query.multiselect(department.get("name"), cb.avg(employee.get("salary")))
             .groupBy(department.get("name"));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Same search as above, but demonstrating dynamic SORT DIRECTION too -
     * another thing that's awkward to express as a plain HQL string but
     * trivial with Criteria (just build a different Order object).
     */
    public List<Employee> findAllSorted(String sortField, boolean ascending) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);

        Order order = ascending ? cb.asc(employee.get(sortField)) : cb.desc(employee.get(sortField));
        query.select(employee).orderBy(order);

        return entityManager.createQuery(query).getResultList();
    }
}
