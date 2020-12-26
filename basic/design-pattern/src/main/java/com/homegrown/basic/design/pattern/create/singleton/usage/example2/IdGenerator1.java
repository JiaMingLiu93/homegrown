package com.homegrown.basic.design.pattern.create.singleton.usage.example2;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author youyu
 */
public class IdGenerator1 {
    private AtomicLong id = new AtomicLong(0);

    private static final IdGenerator1 instance = new IdGenerator1();

    static {
        System.out.println("IdGenerator11");
    }

    private IdGenerator1() {
    }

    public static IdGenerator1 getInstance() {
        return instance;
    }

    public Long getId(){
        return id.incrementAndGet();
    }
}
