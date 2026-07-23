package com.cognizant.springlearn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hands-On 1: Create a Spring Web Project using Maven.
 *
 * The @SpringBootApplication annotation is a convenience annotation that combines:
 *   - @Configuration      : marks this class as a source of bean definitions
 *   - @EnableAutoConfiguration : tells Spring Boot to auto-configure beans based on
 *                                the jars on the classpath (e.g. embedded Tomcat,
 *                                Jackson, DispatcherServlet, because spring-web is present)
 *   - @ComponentScan      : tells Spring to scan this package (and sub-packages)
 *                           for other components, configurations, and services
 */
@SpringBootApplication
public class SpringLearnApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringLearnApplication.class);

	public static void main(String[] args) {
		logger.info("Entering main() method of SpringLearnApplication...");

		SpringApplication.run(SpringLearnApplication.class, args);

		logger.info("SpringLearnApplication started successfully. Application context is up and running.");

		// Hands-On 2: Load SimpleDateFormat bean from Spring XML configuration
		displayDate();

		logger.info("Exiting main() method of SpringLearnApplication.");
	}

	/**
	 * Hands-On 2: Spring Core - Load SimpleDateFormat from Spring Configuration XML.
	 *
	 * Loads date-format.xml (src/main/resources/date-format.xml) into a
	 * ClassPathXmlApplicationContext, retrieves the "dateFormat" bean
	 * (a SimpleDateFormat configured with pattern "dd/MM/yyyy"), and uses it
	 * to parse the string "31/12/2018" into a java.util.Date.
	 */
	public static void displayDate() {
		logger.info("Loading date-format.xml Spring configuration...");

		ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");

		SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);

		try {
			Date parsedDate = format.parse("31/12/2018");
			System.out.println("Parsed Date: " + parsedDate);
			logger.info("Successfully parsed date '31/12/2018' using the 'dateFormat' bean -> {}", parsedDate);
		} catch (ParseException e) {
			logger.error("Failed to parse date using 'dateFormat' bean", e);
		}
	}
}
