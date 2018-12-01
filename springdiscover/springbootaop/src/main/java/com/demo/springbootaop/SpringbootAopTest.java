package com.demo.springbootaop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
public class SpringbootAopTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        SampleAdder sampleAdder = (SampleAdder) context.getBean("sampleAdder");
        System.out.println(sampleAdder.add(1,2));
    }
}
