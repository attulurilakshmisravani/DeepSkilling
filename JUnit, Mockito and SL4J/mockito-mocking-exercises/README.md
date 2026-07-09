# Mocking Dependencies in Spring Tests using Mockito

A Maven Spring Boot project covering all 3 exercises from the assignment.

## How to run this in VS Code (no Maven on PATH needed)

1. Unzip this project.
2. Open the folder in VS Code.
3. Make sure the **Extension Pack for Java** is installed (bundles its own Maven).
4. VS Code auto-detects `pom.xml` and imports the project - wait for dependency
   download to finish (bottom-right progress notifications).
5. Open the **Testing** icon (flask) in the sidebar to see and run all tests,
   or run individual tests from the editor gutter.

Terminal alternative (if Maven is installed):
```
mvn test
```

## Project layout

```
src/main/java/com/example/demo/
  DemoApplication.java            - Spring Boot entry point
  entity/User.java                - JPA entity
  repository/UserRepository.java  - Spring Data repository (empty, no custom query)
  service/UserService.java        - getUserById() delegates to the repository
  controller/UserController.java  - GET /users/{id}

src/test/java/com/example/demo/
  controller/UserControllerTest.java  - Exercise 1
  service/UserServiceTest.java        - Exercise 2
  integration/UserIntegrationTest.java - Exercise 3
```

## Exercise -> Test file map & technique used

| Exercise | What's mocked | Spring context loaded? | Test file | Key annotations |
|----------|---------------|--------------------------|-----------|-----------------|
| 1 | `UserService` (dependency of the controller) | Web layer only | `UserControllerTest.java` | `@WebMvcTest`, `@MockBean`, `MockMvc` |
| 2 | `UserRepository` (dependency of the service) | None at all | `UserServiceTest.java` | `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks` |
| 3 | `UserService` (dependency of the controller) | Full application context | `UserIntegrationTest.java` | `@SpringBootTest`, `@AutoConfigureMockMvc`, `@MockBean` |

### Why these differ

- **Exercise 1 vs Exercise 3** both mock the *same* dependency (`UserService`) and both
  drive the request through `MockMvc`, but the amount of Spring context loaded is
  different:
  - `@WebMvcTest` (Ex 1) only instantiates the web layer - fast, but nothing else in
    your app (like a real database) is available even if you wanted it.
  - `@SpringBootTest` + `@AutoConfigureMockMvc` (Ex 3) boots literally everything -
    slower, but this is the shape you'd use if the test needed other real beans
    alongside the one mock.
- **Exercise 2** doesn't use Spring at all - `MockitoExtension` is plain JUnit 5 +
  Mockito, no application context, no web layer, no `MockMvc`. This is the fastest
  and most isolated way to unit test a service in isolation from its repository.

## Dependencies used

- Spring Boot 3.2.5 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`)
- H2 in-memory database (available for the full-context test in Exercise 3, though
  it's never actually queried since the service is mocked)
- `spring-boot-starter-test` - brings in JUnit 5, Mockito, AssertJ, MockMvc, Spring Test
- Java 17
