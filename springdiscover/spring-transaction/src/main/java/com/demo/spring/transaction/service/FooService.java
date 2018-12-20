package com.demo.spring.transaction.service;

import com.demo.spring.transaction.model.Foo;

/**
 * @author jam
 * @description
 * @create 2018-12-05
 **/
public interface FooService {
    Foo getFoo(String fooName);

    Foo getFoo(String fooName, String barName);

    void insertFoo(Foo foo);

    void updateFoo(Foo foo);

}
