package com.demo.test.bio;

/**
 * @author jam
 * @description
 * @create 2018-12-04
 **/
public class RequestHandler {
    public String handle(String request){
        return "From NIOServer Hello " + request + ".\n";
    }

}
