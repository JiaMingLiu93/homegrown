package com.demo.test.clone;

import java.io.IOException;

/**
 * @author jam
 * @description
 * @create 2018-12-03
 **/
public class CloneTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User();
        user.setName("jam");
        user.setEmail(new Email("qq.com"));

        User user1 = user.deepClone();
        user1.getEmail().setAddress("google.com");

        System.out.println("user="+user);
        System.out.println("user1="+user1);
    }
}
