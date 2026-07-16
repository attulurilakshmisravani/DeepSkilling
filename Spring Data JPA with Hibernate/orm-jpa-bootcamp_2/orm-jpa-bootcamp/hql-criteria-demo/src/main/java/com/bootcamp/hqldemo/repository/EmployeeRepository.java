package com.bootcamp.hqldemo.repository;

import com.bootcamp.hqldemo.dto.DepartmentSalarySummary;
import com.bootcamp.hqldemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Demonstrates the @Query annotation with both HQL/JPQL and native SQL.
 *
 * HQL vs JPQL: JPQL (Java Persistence Query Language) is the query language
 * defined by the JPA SPECIFICATION - portable across any JPA provider.
 * HQL (Hibernate Query Language) is Hibernate's own query language, which
 * predates JPA and is a SUPERSET of JPQL - every valid JPQL query is valid
 * HQL, but HQL adds a few Hibernate-only extras (e.g. more built-in
 * functions, DML-style bulk update/delete, implicit joins that are a little
 * more forgiving). When you write @Query on a Spring Data JPA repository
 * without nativeQuery=true, you are technically writing HQL, since Hibernate
 * is the JPA provider running underneath - but if you stick to what's in the
 * JPA spec, the exact same string is also valid JPQL, so in practice most
 * people use the two terms interchangeably. All the (non-native) queries
 * below are valid as BOTH JPQL and HQL.
 *
 * Reference: Hibernate ORM 4.3 Developer Guide, ch.11 (HQL and JPQL);
 * Baeldung, "Spring Data JPA Query" (features of @Query).
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // --- 1. Basic JPQL/HQL with a named parameter ---
    @Query("select e from Employee e where e.salary > :minSalary")
    List<Employee> findHighEarners(@Param("minSalary") Double minSalary);

    // --- 2. JPQL/HQL with a positional parameter (?1) instead of a named one ---
    @Query("select e from Employee e where e.name like %?1%")
    List<Employee> searchByNameLike(String namePart);

    // --- 3. The HQL/JPQL "fetch" keyword: JOIN FETCH ---
    // Without "fetch", e.department would still be lazily loaded later
    // (an extra SELECT per employee - the classic N+1 problem). JOIN FETCH
    // tells Hibernate to eagerly pull the associated Department in the SAME
    // query, in one round trip, regardless of the entity's own FetchType.
    @Query("select e from Employee e join fetch e.department d where d.name = :deptName")
    List<Employee> findByDepartmentNameWithFetch(@Param("deptName") String deptName);

    // --- 4. Aggregate functions in HQL: AVG, COUNT, GROUP BY ---
    // Returned as a type-safe DTO via a JPQL "constructor expression".
    @Query("select new com.bootcamp.hqldemo.dto.DepartmentSalarySummary(" +
           "  e.department.name, avg(e.salary), count(e)) " +
           "from Employee e group by e.department.name")
    List<DepartmentSalarySummary> averageSalaryByDepartment();

    // --- 5. More aggregate functions: MAX/MIN/SUM over the whole table ---
    @Query("select max(e.salary) from Employee e")
    Double findMaxSalary();

    @Query("select min(e.salary) from Employee e")
    Double findMinSalary();

    @Query("select sum(e.salary) from Employee e where e.department.name = :deptName")
    Double totalSalaryForDepartment(@Param("deptName") String deptName);

    // --- 6. A bulk UPDATE written in HQL/JPQL (needs @Modifying) ---
    @Modifying
    @Query("update Employee e set e.salary = e.salary * (1 + :percent / 100.0) where e.department.name = :deptName")
    int giveRaiseToDepartment(@Param("deptName") String deptName, @Param("percent") Double percent);

    // --- 7. Native Query: nativeQuery = true switches @Query to raw SQL ---
    // Useful when you need a database-specific function/feature, or want to
    // hand-tune a query, that isn't (easily) expressible in JPQL/HQL.
    @Query(value = "SELECT * FROM EMPLOYEE WHERE SALARY BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Employee> findBySalaryRangeNative(Double min, Double max);

    // --- 8. Native query joining across the FK column directly ---
    @Query(value = "SELECT e.* FROM EMPLOYEE e " +
                    "JOIN DEPARTMENT d ON e.department_id = d.id " +
                    "WHERE d.name = ?1 " +
                    "ORDER BY e.hire_date ASC",
           nativeQuery = true)
    List<Employee> findByDepartmentNameNative(String deptName);

    // --- 9. Native query using a DB function (H2's DATEDIFF) not exposed in JPQL ---
    @Query(value = "SELECT * FROM EMPLOYEE WHERE DATEDIFF('YEAR', HIRE_DATE, ?1) >= ?2",
           nativeQuery = true)
    List<Employee> findEmployeesWithTenureNative(LocalDate asOf, int years);
}
