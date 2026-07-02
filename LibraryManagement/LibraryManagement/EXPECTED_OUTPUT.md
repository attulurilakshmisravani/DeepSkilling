# Expected Output — All Exercises

> **Note:** These are the *expected* console/browser outputs you will see when you run each
> exercise yourself with `mvn compile exec:java` (or your IDE's Run button). I could not execute
> Maven/Spring in this sandbox to capture real screenshots (no internet access to Maven Central),
> so treat this file as a guide for what to expect — then swap in your own actual screenshots
> before/after pushing to GitHub if your assignment requires real ones.

---

## Exercise 1 — Basic Spring Application
Run `LibraryManagementApplication` (exercise1-basic-config).

```
BookRepository bean created.
BookService bean created.
Fetching info for book id 101
```

## Exercise 2 — Dependency Injection
Run `LibraryManagementApplication` (exercise2-dependency-injection).

```
BookRepository bean created.
BookRepository injected into BookService.
Book #101: The Spring Framework Handbook
```

## Exercise 3 — Logging with Spring AOP
Run `LibraryManagementApplication` (exercise3-aop-logging).

```
public java.lang.String com.library.service.BookService.getBookInfo(int) executed in 0ms
Book #101: The Spring Framework Handbook
```

## Exercise 4 — Maven Project Setup
Run `LibraryManagementApplication` (exercise4-maven-setup).

```
LibraryManagement Maven project configured successfully.
Dependencies: spring-context, spring-aop, spring-webmvc
Compiler target: Java 1.8
```

## Exercise 5 — Spring IoC Container
Run `LibraryManagementApplication` (exercise5-ioc-container).

```
Spring IoC container loaded. Registered beans:
 - bookRepository
 - bookService
BookRepository bean created.
BookRepository injected into BookService.
Book #101: The Spring Framework Handbook
```

## Exercise 6 — Annotation-based Configuration
Run `LibraryManagementApplication` (exercise6-annotations).

```
Book #101: The Spring Framework Handbook
```

## Exercise 7 — Constructor and Setter Injection
Run `LibraryManagementApplication` (exercise7-constructor-setter-injection).

```
BookRepository injected via constructor.
libraryName injected via setter: Central City Library
[Central City Library] Book #101: The Spring Framework Handbook
```

## Exercise 8 — Basic AOP (Before/After Advice)
Run `LibraryManagementApplication` (exercise8-basic-aop).

```
[BEFORE] Entering method: getBookInfo
[AFTER] Exiting method: getBookInfo
Book #101: The Spring Framework Handbook
```

## Exercise 9 — Spring Boot Application
Run with `mvn spring-boot:run` (exercise9-spring-boot), then in a browser or Postman:

**Startup console (tail):**
```
Tomcat started on port(s): 8080 (http)
Started LibraryManagementApplication in 2.1 seconds
```

**POST** `http://localhost:8080/api/books`
Body: `{"title":"Effective Java","author":"Joshua Bloch"}`
```json
{ "id": 1, "title": "Effective Java", "author": "Joshua Bloch" }
```

**GET** `http://localhost:8080/api/books`
```json
[ { "id": 1, "title": "Effective Java", "author": "Joshua Bloch" } ]
```

H2 console available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:librarydb`).

---

### How to capture your own real screenshots
1. `cd` into each exercise folder and run:
   - Exercises 1–8: `mvn compile exec:java -Dexec.mainClass=com.library.LibraryManagementApplication`
     (or just run the `main` method from your IDE — Eclipse/IntelliJ)
   - Exercise 9: `mvn spring-boot:run`, then hit the endpoints with Postman/browser
2. Screenshot the console output (or Postman response) for each.
3. Drop the images into a `screenshots/` folder in this repo and reference them in this file,
   or replace this file entirely with your captured screenshots.
