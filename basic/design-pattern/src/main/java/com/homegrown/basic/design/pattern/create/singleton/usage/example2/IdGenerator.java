package com.homegrown.basic.design.pattern.create.singleton.usage.example2;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author youyu
 */
public class IdGenerator {
    private AtomicLong id = new AtomicLong(0);

    private static IdGenerator instance;

    private IdGenerator() {
    }

    private static class SingletonHolder{
        static {
            System.out.println("load SingletonHolder");
        }
        private static final IdGenerator instance = new IdGenerator();
    }

    public static IdGenerator getInstance() {
        return SingletonHolder.instance;
    }

    public Long getId(){
        return id.incrementAndGet();
    }
}
