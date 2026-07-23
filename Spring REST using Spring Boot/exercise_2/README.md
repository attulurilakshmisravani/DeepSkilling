# spring-learn

Spring Boot project implementing the three hands-on exercises:

1. **Hello World RESTful Web Service** — `GET /hello` → returns `Hello World!!`
2. **REST - Country Web Service** — `GET /country` → returns India's details as JSON
3. **REST - Get country based on country code** — `GET /countries/{code}` → returns the
   matching country (case-insensitive), or a 404 JSON error if not found

Also included:
- `GET /countries` — returns the full country list
- `CountryNotFoundException` — annotated with `@ResponseStatus(HttpStatus.NOT_FOUND, ...)`
  so an unknown code automatically returns a 404 with a JSON error body
- `country.xml` — Spring bean XML configuration defining the country list
  (loaded by `CountryService` via `ClassPathXmlApplicationContext`)
- `SpringLearnApplicationTests` — MockMvc tests covering `contextLoads()` and the
  country endpoints (valid code, invalid code, and India details)

## Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

The app starts on **port 8083** (configured in `application.properties`).

Try it in a browser, curl, or Postman:

```
http://localhost:8083/hello
http://localhost:8083/country
http://localhost:8083/countries
http://localhost:8083/countries/in
http://localhost:8083/countries/az   (returns 404 - country not found)
```

## Run tests

```bash
mvn test
```

(In Eclipse: right-click `SpringLearnApplicationTests.java` > Run As > JUnit Test)

## Project structure

```
spring-learn/
├── pom.xml
├── src/main/java/com/cognizant/springlearn/
│   ├── SpringLearnApplication.java
│   ├── controller/
│   │   ├── HelloController.java
│   │   └── CountryController.java
│   ├── service/
│   │   ├── CountryService.java
│   │   └── exception/CountryNotFoundException.java
│   └── model/Country.java
├── src/main/resources/
│   ├── application.properties
│   └── country.xml
└── src/test/java/com/cognizant/springlearn/
    └── SpringLearnApplicationTests.java
```
