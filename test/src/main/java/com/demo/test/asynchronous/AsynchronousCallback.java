package com.demo.test.asynchronous;

import java.util.function.Consumer;

/**
 * @author youyu
 * @date 2020/4/5 9:55 PM
 */
public class AsynchronousCallback {
    public static void main(String[] args) {
        System.out.println("pre callback");
        System.out.println(Thread.currentThread().getName());
        callback(System.out::println);
        System.out.println("post callback");
        Test test = new Test();

    }

    private static void callback(Consumer consumer){
        System.out.println(Thread.currentThread().getName());
        consumer.accept("jam");
    }
}
