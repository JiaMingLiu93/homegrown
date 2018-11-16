package com.demo.test.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author jam
 * @description
 * @create 2018-11-15
 **/
public class CglibTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibService.class);
        enhancer.setCallback(new CglibHelloInterceptor());
        CglibService cglibService = (CglibService) enhancer.create();
        System.out.println(cglibService.hello());
    }
}
