# spring-learn

Group: `com.cognizant` | Artifact: `spring-learn`
Dependencies: **Spring Boot DevTools**, **Spring Web**

## Hands-On 1 — Project Setup

1. Generated from https://start.spring.io/ (Group `com.cognizant`, Artifact `spring-learn`, deps: DevTools + Web).
2. Extract into your Eclipse workspace.
3. Build behind the Cognizant proxy:
   ```
   mvn clean package -Dhttp.proxyHost=proxy.cognizant.com -Dhttp.proxyPort=6050 -Dhttps.proxyHost=proxy.cognizant.com -Dhttps.proxyPort=6050 -Dhttp.proxyUser=123456
   ```
4. Import into Eclipse: `File > Import > Maven > Existing Maven Projects` → Browse to the extracted folder → `Finish`.
5. Run `SpringLearnApplication` as a **Java Application** (or `mvn spring-boot:run`). Console logs confirm `main()` executed and the app context started.

### SME Walkthrough Notes

| Folder / File | Purpose |
|---|---|
| `src/main/java` | Application source code — `SpringLearnApplication` and any future components/controllers/services |
| `src/main/resources` | Configuration — `application.properties`, `date-format.xml`, static assets, templates |
| `src/test/java` | Test code — `SpringLearnApplicationTests` (JUnit 5 + `@SpringBootTest`) |
| `SpringLearnApplication.java` | `main()` calls `SpringApplication.run(...)`, which bootstraps the Spring `ApplicationContext`, starts the embedded Tomcat server (because `spring-boot-starter-web` is on the classpath), and triggers component scanning |
| `@SpringBootApplication` | Meta-annotation combining `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan` — this single annotation is why a Spring Boot app needs almost no XML/manual wiring |
| `pom.xml` | Declares the `spring-boot-starter-parent` (dependency/version management), the `spring-web` and `devtools` starters, the test starter, and the `spring-boot-maven-plugin` (packages an executable jar) |

To inspect the **Dependency Hierarchy** in Eclipse: right-click `pom.xml` → `Maven` → or open the POM editor's **Dependency Hierarchy** tab to see every transitive dependency pulled in (e.g. `spring-web`, `spring-webmvc`, `tomcat-embed-core`, `jackson-databind`, `spring-context` — which is what makes `ClassPathXmlApplicationContext` available for Hands-On 2 without any extra dependency).

## Hands-On 2 — Load SimpleDateFormat from Spring XML Configuration

- `src/main/resources/date-format.xml` defines a `dateFormat` bean (`java.text.SimpleDateFormat`) constructed with pattern `dd/MM/yyyy`.
- `SpringLearnApplication.displayDate()`:
  1. Loads the XML file into a `ClassPathXmlApplicationContext`.
  2. Retrieves the bean via `context.getBean("dateFormat", SimpleDateFormat.class)`.
  3. Parses `"31/12/2018"` into a `Date` and prints it.
- `main()` calls `displayDate()` after the Spring Boot context starts, so running `SpringLearnApplication` prints the parsed date to the console.

### Troubleshooting: Tomcat Port Conflict

If port `8080` is already in use, uncomment this line in `src/main/resources/application.properties`:

```properties
server.port=8081
```

## Run

```bash
mvn spring-boot:run
```

Expected console output includes:
```
... Entering main() method of SpringLearnApplication...
... Tomcat started on port(s): 8080 (http)
... SpringLearnApplication started successfully. Application context is up and running.
... Loading date-format.xml Spring configuration...
Parsed Date: Mon Dec 31 00:00:00 ... 2018
... Successfully parsed date '31/12/2018' using the 'dateFormat' bean -> ...
... Exiting main() method of SpringLearnApplication.
```
