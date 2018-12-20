package com.demo.dubbo.spi;

/**
 * @author jam
 * @description
 * @create 2018-12-13
 **/
public class Wrapper1 {
    public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws java.lang.reflect.InvocationTargetException {
        com.demo.dubbo.provider.HelloServiceImpl w;
        try {
            w = ((com.demo.dubbo.provider.HelloServiceImpl) o);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
        try {
            if ("sayHello".equals(n) && p.length == 0) {
                return w.sayHello();
            }
        } catch (Throwable e) {
            throw new java.lang.reflect.InvocationTargetException(e);
        }
        throw new com.alibaba.dubbo.common.bytecode.NoSuchMethodException("Not found method \"" + n + "\" in class com.demo.dubbo.provider.HelloServiceImpl.");
    }
}
