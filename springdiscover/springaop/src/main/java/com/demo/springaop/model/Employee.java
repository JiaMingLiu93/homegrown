package com.demo.springaop.model;

import com.demo.springaop.aspect.Loggable;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
public class Employee {
    private String name;

    public String getName() {
        return name;
    }

    @Loggable
    public void setName(String name) {
        this.name = name;
    }

    public void throwException(){
        throw new RuntimeException("Dummy Exception");
    }
}
