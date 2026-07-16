# hql-criteria-demo

Covers two objectives in one Spring Boot app (H2 in-memory DB, port `8082`):

1. **HQL / JPQL / Native Query** (`EmployeeRepository`, `@Query`)
2. **Criteria Query** (`EmployeeCriteriaService`, `CriteriaBuilder`/`CriteriaQuery`/`Root`/`TypedQuery`)

Sample data (2 departments, 5 employees) seeds automatically on startup.

---

## 1. HQL, JPQL, and Native Query

### HQL vs. JPQL
- **JPQL** (Java Persistence Query Language) is the query language defined
  by the **JPA specification** itself — it's portable across any JPA provider
  (Hibernate, EclipseLink, OpenJPA...).
- **HQL** (Hibernate Query Language) is **Hibernate's own** query language. It
  predates JPA and is a **superset** of JPQL — every valid JPQL query is valid
  HQL, but HQL adds a few Hibernate-only extras (extra built-in functions,
  bulk `UPDATE`/`DELETE` statements, slightly more forgiving implicit joins).
- In a Spring Data JPA repository, `@Query("...")` (without `nativeQuery = true`)
  is technically interpreted by whichever JPA provider is on the classpath —
  Hibernate, by default in Spring Boot — so you're technically writing HQL.
  As long as you stick to what's in the JPA spec (which almost everyone does),
  the exact same string is *also* valid JPQL, so in practice the two terms are
  used interchangeably.

Reference: Hibernate ORM 4.3 Developer Guide, ch. 11.

### `@Query` and its pieces — see `EmployeeRepository`
| Feature | Example method |
|---|---|
| Named parameter | `findHighEarners` — `where e.salary > :minSalary` |
| Positional parameter | `searchByNameLike` — `like %?1%` |
| **`fetch`** keyword | `findByDepartmentNameWithFetch` — `join fetch e.department d` |
| Aggregate functions | `averageSalaryByDepartment` (`avg`, `count`, `group by`), `findMaxSalary`/`findMinSalary` (`max`/`min`), `totalSalaryForDepartment` (`sum`) |
| Bulk DML in HQL | `giveRaiseToDepartment` — an `update ... set ...` statement, requires `@Modifying` |
| **Native Query** | `findBySalaryRangeNative`, `findByDepartmentNameNative`, `findEmployeesWithTenureNative` — all use `@Query(value = "...", nativeQuery = true)` |

**The `fetch` keyword** matters because `Employee.department` is mapped
`FetchType.LAZY` (see `Employee.java`). Without `join fetch`, reading a list of
`Employee`s and then calling `.getDepartment().getName()` on each one would
fire one *extra* SQL query per employee (the N+1 problem). `join fetch` pulls
the associated `Department` in the **same** query.

**`nativeQuery = true`** switches `@Query` from HQL/JPQL to raw SQL, run
directly against the underlying database. Useful when you need a
database-specific function (the tenure example below uses H2's `DATEDIFF`,
which has no JPQL equivalent), want a hand-tuned query, or need syntax that
JPQL simply doesn't support.

### Try it
```
# named parameter
curl "http://localhost:8082/api/hql/high-earners?minSalary=70000"

# positional parameter
curl "http://localhost:8082/api/hql/search?name=riya"

# join fetch (avoids N+1 lazy loads of Department)
curl http://localhost:8082/api/hql/department/Engineering/with-fetch

# aggregate functions: avg + count + group by
curl http://localhost:8082/api/hql/salary-summary-by-department

# aggregate functions: max/min
curl http://localhost:8082/api/hql/salary-stats

# aggregate function: sum, filtered
curl http://localhost:8082/api/hql/department/Sales/total-salary

# bulk update in HQL
curl -X POST "http://localhost:8082/api/hql/department/Sales/give-raise?percent=10"

# native query: salary range
curl "http://localhost:8082/api/hql/native/salary-range?min=70000&max=100000"

# native query: join across the FK column directly in SQL
curl http://localhost:8082/api/hql/native/department/Engineering

# native query using an H2-specific function (DATEDIFF) with no JPQL equivalent
curl "http://localhost:8082/api/hql/native/tenure?asOf=2026-07-16&years=3"
```

---

## 2. Criteria Query

### The need — why not just always use HQL/JPQL strings?
HQL/JPQL queries are fixed strings. That's fine when the query's shape is
known ahead of time, but it breaks down for **dynamic search** screens where
the filters actually applied depend on what the user filled in — e.g. a
search form where name / department / min-salary / max-salary / hire-date
are all *optional*, and any combination might be present at once.

Building that with string concatenation gets ugly fast (easy to get parameter
binding wrong, hard to read, risk of injection if done carelessly — one query
string per combination of filters, or fragile `if`-driven string building).
The **Criteria API** solves this by letting you build the query as **Java
objects** instead of a string:
- **type-safe** — field-name typos become compile-time errors when combined
  with a JPA metamodel generator (not wired up in this demo to keep it
  dependency-free, but mentioned here since it's the natural next step)
- predicates can be **added or skipped conditionally** with plain `if` checks
- the query can be composed, reused, and unit-tested like any other Java code

Reference: Oracle Java EE Tutorial, *Using the Criteria API to Create Queries*.

### Core objects — see `EmployeeCriteriaService`
| Object | Role |
|---|---|
| **`CriteriaBuilder`** | Factory for the whole query: predicates (`equal`, `like`, `greaterThan`, `between`...), aggregate expressions (`avg`, `count`...), and ordering (`asc`/`desc`). Obtained from `entityManager.getCriteriaBuilder()`. |
| **`CriteriaQuery<T>`** | The query itself — what to `select`, `where`, `groupBy`, `orderBy`. Created via `criteriaBuilder.createQuery(Employee.class)`. |
| **`Root<T>`** | Represents the `FROM` clause (the `e` in `from Employee e`). Used to reference fields: `root.get("salary")`, and to `.join(...)` to a related entity. |
| **`TypedQuery<T>`** | The final, executable, type-safe query, obtained from `entityManager.createQuery(criteriaQuery)`. Calling `.getResultList()` runs it. |

`EmployeeCriteriaService.search(...)` is the centerpiece: every one of its
five parameters is optional, and a `Predicate` is added to the query only if
that parameter was actually supplied — exactly the "some filters present, some
not" scenario that's awkward with a plain HQL string.

### Try it
```
# only a name filter
curl "http://localhost:8082/api/criteria/search?nameContains=ra"

# name + department + salary range, all combined dynamically
curl "http://localhost:8082/api/criteria/search?nameContains=a&department=Engineering&minSalary=70000&maxSalary=100000"

# only a department + hire-date filter (name/salary omitted entirely)
curl "http://localhost:8082/api/criteria/search?department=Sales&hiredAfter=2020-01-01"

# aggregate query built with Criteria instead of HQL
curl http://localhost:8082/api/criteria/salary-summary-by-department

# dynamic sort field + direction
curl "http://localhost:8082/api/criteria/sorted?sortField=salary&ascending=false"
```
