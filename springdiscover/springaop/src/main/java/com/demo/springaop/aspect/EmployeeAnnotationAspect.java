package com.demo.springaop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
@Aspect
public class EmployeeAnnotationAspect {
    @Before("@annotation(com.demo.springaop.aspect.Loggable)")
    public void myAdvice(){
        System.out.println("EmployeeAnnotationAspect Executing myAdvice!!");
    }
}
