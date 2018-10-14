package com.demo.concurrence.test;

/**
 * @author jam
 * @description
 * @create 2018-10-14
 **/
public class WaitAndNotifyDemo {
    public static void main(String[] args) {
        Object lock = new Object();
        ThreadWait threadWait = new ThreadWait(lock);
        ThreadNotify threadNotify = new ThreadNotify(lock);
        threadWait.start();
        threadNotify.start();
    }
}

class ThreadWait extends Thread{
    private final Object lock;

    public ThreadWait(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("start to perform thread wait");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end to perform thread wait");
        }
    }
}
class ThreadNotify extends Thread{
    private final Object lock;

    public ThreadNotify(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("start to perform thread notify");
            lock.notify();
            System.out.println("end to perform thread notify");
        }
    }
}
