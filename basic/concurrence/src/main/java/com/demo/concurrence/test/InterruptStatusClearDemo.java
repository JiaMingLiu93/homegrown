package com.demo.concurrence.test;

import java.util.concurrent.TimeUnit;

/**
 * @author jam
 * @description
 * @create 2018-10-10
 **/
public class InterruptStatusClearDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (;;){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().isInterrupted());

                if (Thread.currentThread().isInterrupted()){
                    System.out.println("i am interrupted.");
                    try {
                        InterruptStatusClearDemo.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                System.out.println("not interrupted.");
            }
        });

        thread.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("before thread"+thread.isInterrupted());
        thread.interrupt();
        System.out.println("after thread"+thread.isInterrupted());
    }
}
