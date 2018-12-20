package com.demo.test.jdkDynamicProxy;

/**
 * @author jam
 * @description
 * @create 2018-12-02
 **/
public class UserServiceImpl implements UserService {
    @Override
    public void add() {
        System.out.println("add a user.");
    }

    @Override
    public void delete() {
        System.out.println("delete a user.");
    }
}
