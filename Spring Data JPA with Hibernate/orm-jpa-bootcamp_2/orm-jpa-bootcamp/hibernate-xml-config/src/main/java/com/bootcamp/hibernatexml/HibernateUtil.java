package com.bootcamp.hibernatexml;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Builds and holds the SessionFactory - the heavyweight, thread-safe
 * "core object" that should be created ONCE per application and reused.
 *
 * Configuration.configure() reads hibernate.cfg.xml from the classpath,
 * which in turn pulls in Student.hbm.xml via its <mapping resource="..."/> entry.
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Reads hibernate.cfg.xml from src/main/resources
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
