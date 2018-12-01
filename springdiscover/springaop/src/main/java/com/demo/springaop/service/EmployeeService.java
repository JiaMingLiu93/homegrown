package com.demo.springaop.service;

import com.demo.springaop.model.Employee;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
public class EmployeeService {
    private Employee employee;

    public Employee getEmployee(){
        return this.employee;
    }

    public void setEmployee(Employee e){
        this.employee=e;
    }
}
