package com.bootcamp.hibernateannotation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Same lifecycle as the XML-config demo:
 *   get SessionFactory -> open Session -> begin Transaction ->
 *   do work -> commit Transaction -> close Session
 * The only difference from the XML module is HOW the entity was mapped
 * (annotations on Employee.java vs. a separate Employee.hbm.xml file).
 */
public class App {

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Employee e1 = new Employee("Priya", "Sharma", 75000.0);
            Employee e2 = new Employee("Rahul", "Verma", 82000.0);

            session.save(e1);
            session.save(e2);

            transaction.commit();
            System.out.println("Saved: " + e1 + " and " + e2);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        Session readSession = sessionFactory.openSession();
        try {
            Query<Employee> query = readSession.createQuery(
                    "from Employee where salary > :minSalary order by salary desc",
                    Employee.class);
            query.setParameter("minSalary", 50000.0);
            List<Employee> employees = query.list();

            System.out.println("\nEmployees earning more than 50000:");
            employees.forEach(System.out::println);
        } finally {
            readSession.close();
        }

        HibernateUtil.shutdown();
    }
}
