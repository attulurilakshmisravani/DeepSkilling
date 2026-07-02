package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Loads the central IoC container configuration and resolves beans from it
public class LibraryManagementApplication {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("Spring IoC container loaded. Registered beans:");
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(" - " + beanName);
        }

        BookService bookService = (BookService) context.getBean("bookService");
        System.out.println(bookService.getBookInfo(101));
    }
}
