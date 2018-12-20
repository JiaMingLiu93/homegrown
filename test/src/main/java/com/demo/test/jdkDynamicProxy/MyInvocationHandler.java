package com.demo.test.jdkDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jam
 * @description
 * @create 2018-12-02
 **/
public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object o){
        super();
        this.target = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before call add method.");
        Object result = method.invoke(target, args);
        System.out.println("after call add method.");
        return result;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),target.getClass().getInterfaces(),this);
    }
}
