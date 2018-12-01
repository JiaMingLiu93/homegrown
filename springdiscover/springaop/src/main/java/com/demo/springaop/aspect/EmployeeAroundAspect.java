package com.demo.springaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
@Aspect
public class EmployeeAroundAspect {
    @Around("execution(* com.demo.springaop.model.Employee.getName())")
    public Object employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("EmployeeAroundAspect Before invoking getName() method");
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("EmployeeAroundAspect After invoking getName() method. Return value="+value);
        return value;
    }
}
