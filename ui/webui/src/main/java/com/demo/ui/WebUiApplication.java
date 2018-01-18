package com.demo.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo")
public class WebUiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebUiApplication.class);
    }
}
