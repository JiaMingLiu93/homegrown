package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jam
 * @description
 * @create 2018-08-28
 **/
@SpringBootApplication
public class SpringBootEventTest {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringApplicationTest.class);
        springApplication.addListeners(event -> {
            System.err.println(event.getClass());
        });
        springApplication.run(args);
    }
}
