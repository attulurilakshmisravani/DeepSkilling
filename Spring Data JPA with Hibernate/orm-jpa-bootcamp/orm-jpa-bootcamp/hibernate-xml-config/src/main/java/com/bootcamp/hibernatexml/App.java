package com.bootcamp.hibernatexml;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Demonstrates the classic Hibernate lifecycle using XML configuration:
 *
 *   1. Get the SessionFactory (built once from hibernate.cfg.xml + Student.hbm.xml)
 *   2. Open a Session
 *   3. Begin a Transaction
 *   4. Do work (save + query)
 *   5. Commit the Transaction
 *   6. Close the Session
 */
public class App {

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // --- INSERT a couple of students ---
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Student s1 = new Student("Sravani", "K", "sravani@example.com");
            Student s2 = new Student("Arjun", "Reddy", "arjun@example.com");

            session.save(s1);
            session.save(s2);

            transaction.commit();
            System.out.println("Saved: " + s1 + " and " + s2);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        // --- READ them back with an HQL query (database-independent query) ---
        Session readSession = sessionFactory.openSession();
        try {
            Query<Student> query = readSession.createQuery(
                    "from Student order by id", Student.class);
            List<Student> students = query.list();

            System.out.println("\nAll students in the database:");
            students.forEach(System.out::println);
        } finally {
            readSession.close();
        }

        HibernateUtil.shutdown();
    }
}
