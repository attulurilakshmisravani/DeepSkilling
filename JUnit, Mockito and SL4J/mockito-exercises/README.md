# Mockito Hands-On Exercises

A single Maven project covering all 7 exercises, using JUnit 5 (Jupiter)
and Mockito.

## Project Structure

```
mockito-exercises/
├── pom.xml
└── src
    ├── main/java/com/example/mockito/
    │   ├── ExternalApi.java   (the interface we mock in every exercise)
    │   └── MyService.java     (the class under test — depends on ExternalApi)
    └── test/java/com/example/mockito/
        ├── MockingAndStubbingTest.java        -> Exercise 1
        ├── VerifyingInteractionsTest.java      -> Exercise 2
        ├── ArgumentMatchingTest.java           -> Exercise 3
        ├── HandlingVoidMethodsTest.java        -> Exercise 4
        ├── MultipleReturnsTest.java            -> Exercise 5
        ├── VerifyingInteractionOrderTest.java  -> Exercise 6
        └── VoidMethodsWithExceptionsTest.java  -> Exercise 7
```

## Exercise Summary

1. **Mocking and Stubbing** — mocks `ExternalApi`, stubs `getData()` with
   `when(...).thenReturn(...)`, and checks `MyService.fetchData()` returns
   the stubbed value.
2. **Verifying Interactions** — uses `verify(mock).method()` and
   `verify(mock, times(1))` / `verify(mock, never())` to confirm a method
   was (or wasn't) called.
3. **Argument Matching** — uses `anyString()`, `eq()`, and `startsWith()`
   from `ArgumentMatchers` to stub/verify calls regardless of, or based on,
   specific argument values.
4. **Handling Void Methods** — uses `doNothing().when(mock).method(...)`
   to stub a void method, then verifies it was called.
5. **Multiple Returns** — uses chained `.thenReturn(a).thenReturn(b)...`
   (and the varargs shorthand `.thenReturn(a, b, c)`) so consecutive calls
   return different values.
6. **Verifying Interaction Order** — uses Mockito's `InOrder` to confirm
   `performAction()` is called before `logMessage()`.
7. **Void Methods with Exceptions** — uses
   `doThrow(new RuntimeException(...)).when(mock).method(...)` to make a
   void method throw, and confirms the exception propagates with
   `assertThrows()`.

## How to Run

### Option A: Maven (recommended)

```bash
cd mockito-exercises
mvn test
```

### Option B: VS Code

1. Install the **Extension Pack for Java** (bundles Maven + Test Runner
   support).
2. **File → Open Folder** → select the `mockito-exercises` folder.
3. Wait for VS Code to download dependencies (shown in the status bar).
4. Open the **Testing** sidebar (flask icon) and run any test class, or all
   of them at once.

### Option C: Eclipse / IntelliJ

Import as an existing Maven project, let it resolve dependencies, then
right-click any test class → **Run As/Run** → **JUnit Test**.

## Submitting via GitHub

```bash
cd mockito-exercises
git init
git add .
git commit -m "Mockito Hands-On Exercises 1-7"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```
