# Advanced JUnit Testing Exercises

A single Maven project covering all 5 exercises from the assignment,
using JUnit 5 (Jupiter).

## Project Structure

```
junit-advanced-exercises/
├── pom.xml
└── src
    ├── main/java/com/example/exercises/
    │   ├── EvenChecker.java          (used by Exercise 1)
    │   ├── ExceptionThrower.java     (used by Exercise 4)
    │   └── PerformanceTester.java    (used by Exercise 5)
    └── test/java/com/example/exercises/
        ├── EvenCheckerTest.java       -> Exercise 1: Parameterized Tests
        ├── AllTests.java              -> Exercise 2: Test Suites and Categories
        ├── OrderedTests.java          -> Exercise 3: Test Execution Order
        ├── ExceptionThrowerTest.java  -> Exercise 4: Exception Testing
        └── PerformanceTesterTest.java -> Exercise 5: Timeout and Performance Testing
```

## Exercise Summary

1. **Parameterized Tests** — `EvenCheckerTest` uses `@ParameterizedTest` with
   `@ValueSource` (even/odd numbers) and `@CsvSource` (input/expected pairs)
   to test `EvenChecker.isEven()`.
2. **Test Suites and Categories** — `AllTests` uses `@Suite` and
   `@SelectClasses` (from `junit-platform-suite`) to group all four test
   classes and run them together.
3. **Test Execution Order** — `OrderedTests` uses `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)`
   with `@Order(1)`, `@Order(2)`, `@Order(3)` to guarantee execution order.
4. **Exception Testing** — `ExceptionThrowerTest` uses `assertThrows()` to
   verify `ExceptionThrower.throwException()` throws an
   `IllegalArgumentException` for negative input, plus `assertDoesNotThrow()`
   for valid input.
5. **Timeout and Performance Testing** — `PerformanceTesterTest` shows three
   ways to test timeouts: the declarative `@Timeout` annotation, and the
   programmatic `assertTimeout()` / `assertTimeoutPreemptively()` methods.

## How to Run

### Option A: Using Maven (recommended)

```bash
cd junit-advanced-exercises
mvn test
```

Run only the suite (Exercise 2), which executes everything:

```bash
mvn test -Dtest=AllTests
```

### Option B: Import into Eclipse / IntelliJ

1. Open the IDE and choose **Import > Existing Maven Project**.
2. Select the `junit-advanced-exercises` folder (the one containing `pom.xml`).
3. Let the IDE download the JUnit 5 dependencies.
4. Right-click any test class (or `AllTests` for everything) and choose
   **Run As > JUnit Test**.

### Option C: VS Code

1. Install the **Extension Pack for Java** and **Test Runner for Java**.
2. Open the `junit-advanced-exercises` folder.
3. Use the Testing sidebar (flask icon) to run individual tests or the whole
   suite.

## Submitting via GitHub

```bash
cd junit-advanced-exercises
git init
git add .
git commit -m "Advanced JUnit Testing Exercises 1-5"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```
