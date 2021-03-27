package com.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hello world!
 * For reading spring source code.
 *
 */
@Configuration
@ComponentScan
@Transactional
public class App {
    @Bean
    MessageService mockMessageService() {
        return () -> "Hello World!";
    }

    public static void main( String[] args ) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(App.class);
        MessagePrinter printer = context.getBean(MessagePrinter.class);
        printer.printMessage();
    }
}
