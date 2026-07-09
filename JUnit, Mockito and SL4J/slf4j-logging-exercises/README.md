# Logging using SLF4J - Exercises 1-3

A plain Java (non-Spring) Maven project demonstrating SLF4J + Logback logging.

## How to run this in VS Code (no Maven on PATH needed)

1. Unzip this project and open the folder in VS Code.
2. Make sure the **Extension Pack for Java** is installed (bundles its own Maven).
3. Wait for VS Code to import the project and download dependencies.
4. Open any of the 3 classes below and click **Run** above the `main` method
   (VS Code shows this automatically for classes with a `main` method):
   - `LoggingExample.java` (Exercise 1)
   - `ParameterizedLoggingExample.java` (Exercise 2)
   - `AppenderExample.java` (Exercise 3)

Terminal alternative (if Maven is installed):
```
mvn compile exec:java -Dexec.mainClass="com.example.logging.LoggingExample"
mvn compile exec:java -Dexec.mainClass="com.example.logging.ParameterizedLoggingExample"
mvn compile exec:java -Dexec.mainClass="com.example.logging.AppenderExample"
```

## Project layout

```
pom.xml                                  - SLF4J + Logback dependencies
src/main/resources/logback.xml           - Console + File appender config (Exercise 3)
src/main/java/com/example/logging/
  LoggingExample.java                    - Exercise 1
  ParameterizedLoggingExample.java       - Exercise 2
  AppenderExample.java                   - Exercise 3
```

## Exercise -> file map

| Exercise | Topic | File |
|----------|-------|------|
| 1 | Error and warning level logging | `LoggingExample.java` |
| 2 | Parameterized logging with `{}` placeholders | `ParameterizedLoggingExample.java` |
| 3 | Console + File appenders | `logback.xml` + `AppenderExample.java` |

## Notes

- **Versions**: the assignment specifies `slf4j-api 1.7.30` and
  `logback-classic 1.2.3`. This project uses `1.7.36` and `1.2.13` instead -
  same 1.x API, just later patch releases that fix a couple of known
  security issues in the older versions. All the code is identical either
  way; only the version numbers in `pom.xml` differ.
- **`logback.xml` applies to the whole project**, not just Exercise 3. Since
  it's on the classpath for everything in `src/main/resources`, running
  `LoggingExample` or `ParameterizedLoggingExample` will *also* write to
  `app.log` once you've added it, in addition to the console. That's normal -
  logging configuration is global per application, not per-class.
- **Where is `app.log`?** It gets created in whatever directory you run the
  program from - if you run via VS Code's Run button, that's usually the
  project root, so look for `app.log` next to `pom.xml` after running.
- **Exercise 2 talking point**: the commented-out concatenation example in
  `ParameterizedLoggingExample` is worth mentioning if asked *why*
  parameterized logging is better - `"User " + username + "..."` always
  builds the full string even if that log level is switched off, while
  `logger.debug("User {} ...", username)` only formats the string if DEBUG
  is actually enabled.
