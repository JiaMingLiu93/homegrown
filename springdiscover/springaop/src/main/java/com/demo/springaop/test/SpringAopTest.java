package com.demo.springaop.test;

import com.demo.springaop.model.Employee;
import com.demo.springaop.service.EmployeeService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
public class SpringAopTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        EmployeeService employeeService = context.getBean(EmployeeService.class);
        Employee employee = employeeService.getEmployee();
        employee.setName("Jam Lau");
        System.out.println(employee.getName());
        employee.throwException();
        context.close();
    }
}
