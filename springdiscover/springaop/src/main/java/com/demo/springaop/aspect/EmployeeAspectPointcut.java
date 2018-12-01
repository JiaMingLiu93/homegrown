package com.demo.springaop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
@Aspect
public class EmployeeAspectPointcut {
    @Pointcut("execution(public String getName())")
    public void getNamePointcut(){}

    @Before("getNamePointcut()")
    public void loggingAdvice(){
        System.out.println("Executing loggingAdvice on getName()");
    }

    @Before("getNamePointcut()")
    public void secondAdvice(){
        System.out.println("Executing secondAdvice on getName()");
    }

    //Pointcut to execute on all the methods of classes in a package
    @Pointcut("within(com.demo.springaop.service.*)")
    public void allMethodsPointcut(){}

    @Before("allMethodsPointcut()")
    public void allServiceMethodsAdvice(){
        System.out.println("Before executing service method");
    }
}
