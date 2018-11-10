package com.demo.dubbo.provider;

import com.demo.server.api.HelloService;

/**
 * @author jam
 * @description
 * @create 2018-10-31
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "hello,Jam!";
    }
}
