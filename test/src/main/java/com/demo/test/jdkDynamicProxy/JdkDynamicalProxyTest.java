package com.demo.test.jdkDynamicProxy;

/**
 * @author jam
 * @description
 * @create 2018-12-02
 **/
public class JdkDynamicalProxyTest {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(userService);
        UserService service = (UserService) myInvocationHandler.getProxy();
        service.add();
        service.delete();
    }
}
