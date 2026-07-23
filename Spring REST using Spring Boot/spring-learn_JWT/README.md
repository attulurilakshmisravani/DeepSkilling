# spring-learn

Spring Boot project implementing the REST + Spring Security/JWT hands-on exercises:

**REST services:**
1. **Hello World RESTful Web Service** — `GET /hello` → returns `Hello World!!`
2. **REST - Country Web Service** — `GET /country` → returns India's details as JSON
3. **REST - Get country based on country code** — `GET /countries/{code}` → returns the
   matching country (case-insensitive), or a 404 JSON error if not found
4. **REST - Get all countries** — `GET /countries`

**Security / JWT (this hands-on):**
5. `SecurityConfig` — secures the app with Spring Security. Two in-memory users are
   defined: `admin` / `pwd` (role ADMIN) and `user` / `pwd` (role USER). Registers
   `JwtAuthorizationFilter` so requests can also authenticate via a JWT bearer token.
6. `AuthenticationController` — `GET /authenticate` (HTTP Basic auth required). Reads
   the `Authorization` header, decodes the username, and returns a signed JWT valid
   for 20 minutes: `{"token": "eyJhbGciOiJIUzI1NiJ9. ..."}`
7. `JwtAuthorizationFilter` — a `BasicAuthenticationFilter` that intercepts every
   request; if the `Authorization` header is `Bearer <token>`, it validates the JWT
   and marks the request authenticated so it can reach the target controller.

Also included:
- `CountryNotFoundException` — annotated with `@ResponseStatus(HttpStatus.NOT_FOUND, ...)`
  so an unknown code automatically returns a 404 with a JSON error body
- `country.xml` — Spring bean XML configuration defining the country list
  (loaded by `CountryService` via `ClassPathXmlApplicationContext`)
- `SpringLearnApplicationTests` — MockMvc tests (using `spring-security-test`'s
  `httpBasic()` post-processor) covering `contextLoads()`, country endpoints
  (valid/invalid code), unauthenticated/role-based access to `/countries`, and
  `/authenticate` returning a token

## Trying out the JWT flow with curl

```bash
# 1. Get a token (HTTP Basic auth)
curl -s -u user:pwd http://localhost:8090/authenticate
# -> {"token":"eyJhbGciOiJIUzI1NiJ9...."}

# 2. Use the token as a Bearer credential on any other endpoint
curl -s -H "Authorization: Bearer <paste-token-here>" http://localhost:8090/countries

# 3. An invalid/expired token is rejected
curl -s -H "Authorization: Bearer garbage" http://localhost:8090/countries
```

Note: `SecurityConfig`'s final `configure(HttpSecurity)` leaves
`.antMatchers("/countries").hasRole("USER")` commented out (matching the hands-on
doc) once the JWT filter is wired in, so at that stage `/countries` just requires
any authenticated user via `.anyRequest().authenticated()`. Uncomment that line if
you want `/countries` restricted to role `USER` specifically again.

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
│   │   ├── CountryController.java
│   │   └── AuthenticationController.java
│   ├── security/
│   │   ├── SecurityConfig.java
│   │   └── JwtAuthorizationFilter.java
│   ├── service/
│   │   ├── CountryService.java
│   │   └── exception/CountryNotFoundException.java
│   └── model/Country.java
├── src/main/resources/
│   ├── application.properties  (server.port=8090)
│   └── country.xml
└── src/test/java/com/cognizant/springlearn/
    └── SpringLearnApplicationTests.java
```
