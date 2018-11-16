package com.demo.test.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author jam
 * @description
 * @create 2018-11-15
 **/
public class CglibHelloInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before calling "+method.getName());
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("after calling "+method.getName());
        return o1;
    }
}
