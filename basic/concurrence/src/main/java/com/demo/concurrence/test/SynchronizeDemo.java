package com.demo.concurrence.test;

/**
 * @author jam
 * @description
 * @create 2018-10-10
 **/
public class SynchronizeDemo {
    public static void main(String[] args) {
        new Thread(SynchronizeDemo::print,"demo-1").start();
        new Thread(SynchronizeDemo::print,"demo-2").start();
    }

    private static synchronized void print(){
        System.out.println("print current thread:"+Thread.currentThread().getName());
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            SynchronizeDemo.class.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("end print");
    }
}
