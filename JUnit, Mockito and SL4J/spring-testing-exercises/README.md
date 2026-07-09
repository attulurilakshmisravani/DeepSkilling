# Spring Testing Exercises (JUnit 5 + Mockito)

A single Maven Spring Boot project covering all 9 exercises from the assignment.

## How to run this in VS Code (no Maven on PATH needed)

1. Unzip this project.
2. Open the folder in VS Code.
3. Make sure you have the **Extension Pack for Java** installed (this bundles its own
   Maven, so you don't need Maven configured on your system PATH).
4. VS Code will detect `pom.xml` and auto-import the project. Wait for it to finish
   downloading dependencies (bottom-right progress notifications).
5. Open the **Testing** icon in the left sidebar (flask icon) — all test classes
   below will appear there. Click the play button next to any class/method to run it,
   or click "Run All Tests" at the top.

Alternatively, in a terminal with Maven installed:
```
mvn test
```

## Project layout

```
src/main/java/com/example/demo/
  DemoApplication.java              - Spring Boot entry point
  entity/User.java                  - JPA entity
  repository/UserRepository.java    - Spring Data repository (+ custom query, Ex 7)
  service/CalculatorService.java    - Ex 1
  service/UserService.java          - Ex 2, 5, 6, 7
  controller/UserController.java    - Ex 3, 5, 8
  exception/UserNotFoundException.java
  exception/GlobalExceptionHandler.java  - Ex 8 @ControllerAdvice

src/test/java/com/example/demo/
  service/CalculatorServiceTest.java              - Exercise 1
  service/UserServiceTest.java                    - Exercises 2, 6, 7 (Mockito unit tests)
  controller/UserControllerTest.java              - Exercises 3, 5, 8 (MockMvc, @WebMvcTest)
  repository/UserRepositoryTest.java              - Exercise 7 (@DataJpaTest against H2)
  integration/UserIntegrationTest.java            - Exercise 4 (@SpringBootTest, full stack)
  service/CalculatorServiceParameterizedTest.java - Exercise 9 (@ParameterizedTest)
```

## Exercise -> Test file map

| Exercise | Topic                                   | Test file |
|----------|------------------------------------------|-----------|
| 1        | Basic unit test for a service method      | `CalculatorServiceTest.java` |
| 2        | Mocking a repository in a service test    | `UserServiceTest.java` |
| 3        | REST controller test with MockMvc         | `UserControllerTest.java` |
| 4        | Full Spring Boot integration test         | `UserIntegrationTest.java` |
| 5        | Controller POST endpoint test             | `UserControllerTest.java` |
| 6        | Service exception handling test           | `UserServiceTest.java` |
| 7        | Custom repository query test              | `UserRepositoryTest.java` + `UserServiceTest.java` |
| 8        | Controller exception handling (@ControllerAdvice) | `UserControllerTest.java` |
| 9        | Parameterized test                        | `CalculatorServiceParameterizedTest.java` |

## Notes on technique used per exercise

- **Ex 1** — Plain JUnit 5, no Spring context, no mocking needed (no dependencies to fake).
- **Ex 2** — `@ExtendWith(MockitoExtension.class)` + `@Mock`/`@InjectMocks`. Fast, no Spring
  context started at all.
- **Ex 3** — `@WebMvcTest(UserController.class)` loads only the web layer; `@MockBean`
  replaces `UserService` so no real DB is touched.
- **Ex 4** — `@SpringBootTest` + `@AutoConfigureMockMvc` boots the *entire* application
  context (real service, real repository, real embedded H2 DB) — this is what makes it
  an "integration" test rather than a slice test.
- **Ex 5** — Same `@WebMvcTest` slice as Ex 3, just exercising the POST endpoint.
- **Ex 6** — Added a `getUserByIdOrThrow()` method to `UserService` that throws a custom
  `UserNotFoundException` (a `NoSuchElementException` subtype) instead of returning null,
  so there's something meaningful to assert with `assertThrows`.
- **Ex 7** — Added `findByName()` to the repository, tested two ways: directly against
  H2 with `@DataJpaTest`, and indirectly through the mocked service in `UserServiceTest`.
- **Ex 8** — `@ControllerAdvice` catches `NoSuchElementException` and returns 404 +
  "User not found"; tested at the `@WebMvcTest` slice level by importing the advice
  class alongside the controller.
- **Ex 9** — `@ParameterizedTest` with `@CsvSource` (multiple args), `@ValueSource` (ints),
  and `@ValueSource` (strings) to show the different data-source styles.

## Dependencies used

- Spring Boot 3.2.5 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`)
- H2 in-memory database (for `@DataJpaTest` / `@SpringBootTest`)
- `spring-boot-starter-test` — brings in JUnit 5, Mockito, AssertJ, MockMvc, Spring Test
- Java 17
