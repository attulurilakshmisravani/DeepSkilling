# Advanced Mockito Hands-On Exercises

A single Maven project covering all 5 exercises, using JUnit 5 (Jupiter)
and Mockito. The original assignment gave you finished solution code but
not the classes being tested (`Repository`, `Service`, `RestClient`, etc.)
— those supporting classes are implemented here so the whole project
compiles and runs end-to-end.

## Project Structure

```
mockito-advanced-exercises/
├── pom.xml
└── src
    ├── main/java/com/example/mockitoadvanced/
    │   ├── Repository.java       (Exercise 1 & 5)
    │   ├── Service.java          (Exercise 1 & 5)
    │   ├── RestClient.java       (Exercise 2)
    │   ├── ApiService.java       (Exercise 2)
    │   ├── FileReader.java       (Exercise 3 — our own interface, not java.io)
    │   ├── FileWriter.java       (Exercise 3 — our own interface, not java.io)
    │   ├── FileService.java      (Exercise 3)
    │   ├── NetworkClient.java    (Exercise 4)
    │   └── NetworkService.java   (Exercise 4)
    └── test/java/com/example/mockitoadvanced/
        ├── ServiceTest.java             -> Exercise 1: Mocking Databases and Repositories
        ├── ApiServiceTest.java          -> Exercise 2: Mocking External Services (RESTful APIs)
        ├── FileServiceTest.java         -> Exercise 3: Mocking File I/O
        ├── NetworkServiceTest.java      -> Exercise 4: Mocking Network Interactions
        └── MultiReturnServiceTest.java  -> Exercise 5: Mocking Multiple Return Values
```

## Exercise Summary

1. **Mocking Databases and Repositories** — mocks `Repository`, stubs
   `getData()`, and verifies `Service.processData()` prefixes it with
   `"Processed "`.
2. **Mocking External Services (RESTful APIs)** — mocks `RestClient`,
   stubs `getResponse()`, and verifies `ApiService.fetchData()` prefixes
   it with `"Fetched "`.
3. **Mocking File I/O** — mocks both `FileReader` and `FileWriter`, stubs
   `read()`, and verifies `FileService.processFile()` both returns the
   processed content and writes it out via the mocked writer.
4. **Mocking Network Interactions** — mocks `NetworkClient`, stubs
   `connect()`, and verifies `NetworkService.connectToServer()` prefixes
   it with `"Connected to "`.
5. **Mocking Multiple Return Values** — stubs `Repository.getData()` with
   chained `.thenReturn(a).thenReturn(b)` so two consecutive calls to
   `Service.processData()` return different results.

## How to Run

### Option A: Maven (recommended)

```bash
cd mockito-advanced-exercises
mvn test
```

### Option B: VS Code

1. Install the **Extension Pack for Java** (bundles Maven + Test Runner
   support).
2. **File → Open Folder** → select `mockito-advanced-exercises`.
3. Wait for VS Code to finish resolving dependencies (status bar).
4. Open the **Testing** sidebar (flask icon) and run any test class, or
   all of them at once.

### Option C: Eclipse / IntelliJ

Import as an existing Maven project, let it resolve dependencies, then
right-click a test class → **Run As/Run** → **JUnit Test**.

## Installations Needed

- JDK 17+ (`java -version`)
- Maven (`mvn -version`)
- No Mockito install needed — it's a declared dependency in `pom.xml` and
  downloads automatically on first build (requires internet access once).

## Submitting via GitHub

```bash
cd mockito-advanced-exercises
git init
git add .
git commit -m "Advanced Mockito Hands-On Exercises 1-5"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```
