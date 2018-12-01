package com.demo.springaop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
@Aspect
public class EmployeeAspect {
    @Before("execution(public String getName())")
    public void getNameAdvice(){
        System.out.println("EmployeeAspect Executing Advice on getName()");
    }

    @Before("execution(* com.demo.springaop.service.*.get*())")
    public void getAllAdvice(){
        System.out.println("EmployeeAspect Service method getter called");
    }
}
