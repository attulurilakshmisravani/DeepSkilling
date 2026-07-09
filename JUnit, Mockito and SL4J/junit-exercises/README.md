# JUnit Basic Testing Exercises

This Maven project contains the solution code for all four JUnit exercises.

## Project Structure

```
junit-exercises/
├── pom.xml
├── README.md
└── src
    ├── main/java/com/example/
    │   └── Calculator.java              # Class under test (used by Exercises 2-4)
    └── test/java/com/example/
        ├── Exercise1_SetupTest.java     # Exercise 1: Setting Up JUnit
        ├── Exercise2_CalculatorTest.java# Exercise 2: Writing Basic JUnit Tests
        ├── Exercise3_AssertionsTest.java# Exercise 3: Assertions in JUnit
        └── Exercise4_FixtureTest.java   # Exercise 4: AAA Pattern, Fixtures, Setup/Teardown
```

## Exercise 1: Setting Up JUnit
- The JUnit 4.13.2 dependency is declared in `pom.xml` under `<dependencies>`.
- `Exercise1_SetupTest.java` is a minimal test class that confirms the setup works.

## Exercise 2: Writing Basic JUnit Tests
- `Calculator.java` is a simple Java class with `add`, `subtract`, `multiply`, and `divide` methods.
- `Exercise2_CalculatorTest.java` contains basic JUnit tests for each method.

## Exercise 3: Assertions in JUnit
- `Exercise3_AssertionsTest.java` demonstrates various JUnit assertions:
  `assertEquals`, `assertTrue`, `assertFalse`, `assertNull`, `assertNotNull`,
  `assertSame`, `assertNotSame`, `assertArrayEquals`, and exception testing with `fail`.

## Exercise 4: AAA Pattern, Test Fixtures, Setup and Teardown
- `Exercise4_FixtureTest.java` demonstrates:
  - The Arrange-Act-Assert (AAA) pattern in each test method.
  - `@Before` / `@After` to set up and tear down a fresh fixture before/after every test.
  - `@BeforeClass` / `@AfterClass` for one-time setup/teardown for the whole test class.

## How to Run

### Using Maven (command line)
```bash
mvn test
```

### Using an IDE (Eclipse / IntelliJ)
1. Import the project as a Maven project.
2. Right-click the `src/test/java` folder (or an individual test class) and choose
   "Run As > JUnit Test".

## Submitting via GitHub
1. Initialize a git repository in this folder (if not already done):
   ```bash
   git init
   git add .
   git commit -m "Add JUnit basic testing exercises 1-4"
   ```
2. Create a new repository on GitHub, then link and push:
   ```bash
   git remote add origin https://github.com/<your-username>/<your-repo>.git
   git branch -M main
   git push -u origin main
   ```
